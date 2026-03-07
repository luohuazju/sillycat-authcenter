import os
import json
from urllib.parse import urljoin

from fastapi import FastAPI, Request, HTTPException, Response
from fastapi.responses import RedirectResponse, HTMLResponse
from starlette.middleware.sessions import SessionMiddleware
from authlib.integrations.starlette_client import OAuth, OAuthError
from dotenv import load_dotenv
from itsdangerous import URLSafeTimedSerializer

load_dotenv()

APP_BASE_URL = os.getenv("APP_BASE_URL", "http://localhost:8000")
OIDC_ISSUER = os.getenv("OIDC_ISSUER")
OIDC_CLIENT_ID = os.getenv("OIDC_CLIENT_ID")
OIDC_CLIENT_SECRET = os.getenv("OIDC_CLIENT_SECRET")
APP_SECRET = os.getenv("APP_SECRET", "dev-secret")

if not OIDC_ISSUER or not OIDC_CLIENT_ID:
    raise RuntimeError("Missing OIDC_ISSUER or OIDC_CLIENT_ID in .env")

# 如果是 confidential client，需要 client secret
if not OIDC_CLIENT_SECRET:
    print("⚠️ OIDC_CLIENT_SECRET is empty. If your Keycloak client uses Client Authentication (confidential), set the secret.")

app = FastAPI()
# SessionMiddleware 仍然需要，因为 authlib 的 OAuth 流程需要用它来存储临时的 state 数据
# 但我们不用它来存储用户登录信息，而是用独立的签名 cookie
app.add_middleware(
    SessionMiddleware, 
    secret_key=APP_SECRET, 
    same_site="lax", 
    https_only=False,
    max_age=3600,  # 1小时
    session_cookie="session"  # 明确指定 cookie 名称
)

# 用于签名 cookie 的序列化器（用于存储用户登录信息）
serializer = URLSafeTimedSerializer(APP_SECRET)

def get_user_from_cookie(request: Request):
    """从 cookie 中获取用户信息"""
    user_cookie = request.cookies.get("user_data")
    if not user_cookie:
        return None
    try:
        # 验证并解析 cookie（有效期 1 小时）
        user_data = serializer.loads(user_cookie, max_age=3600)
        return user_data
    except Exception as e:
        print(f"Cookie validation failed: {e}")
        return None

def set_user_cookie(response: Response, user_data: dict):
    """设置用户信息到 cookie"""
    user_cookie = serializer.dumps(user_data)
    response.set_cookie(
        key="user_data",
        value=user_cookie,
        max_age=3600,
        httponly=True,
        samesite="lax"
    )

oauth = OAuth()
oauth.register(
    name="keycloak",
    server_metadata_url=urljoin(OIDC_ISSUER.rstrip("/") + "/", ".well-known/openid-configuration"),
    client_id=OIDC_CLIENT_ID,
    client_secret=OIDC_CLIENT_SECRET or None,
    client_kwargs={
        "scope": "openid profile email",
        # 如果你配置了 public client + PKCE，可加：
        # "code_challenge_method": "S256",
    },
)

@app.get("/", response_class=HTMLResponse)
async def home(request: Request):
    user = get_user_from_cookie(request)
    print("Home - User from cookie:", user)
    
    if not user:
        return HTMLResponse(
            "<h2>未登录</h2>"
            '<p><a href="/login">使用 Keycloak 登录</a></p>'
        )
    info = user.get("userinfo", {})
    return HTMLResponse(
        "<h2>已登录</h2>"
        f"<p><b>用户名:</b> {info.get('preferred_username')}</p>"
        f"<p><b>Email:</b> {info.get('email')}</p>"
        f"<p><b>Sub:</b> {info.get('sub')}</p>"
        '<p><a href="/protected">访问受保护页面</a></p>'
        '<p><a href="/logout">退出</a></p>'
    )

@app.get("/login")
async def login(request: Request):
    redirect_uri = f"{APP_BASE_URL}/auth/callback"
    return await oauth.keycloak.authorize_redirect(request, redirect_uri)

@app.get("/auth/callback")
async def auth_callback(request: Request):
    try:
        token = await oauth.keycloak.authorize_access_token(request)
    except OAuthError as e:
        raise HTTPException(status_code=400, detail=f"Auth error: {e}")

    # 从 userinfo 读取基础信息（需要 scope: openid profile email）
    userinfo = await oauth.keycloak.userinfo(token=token)

    print("Userinfo:", userinfo)

    # 准备用户数据（不包含完整 token，只保存必要信息）
    user_data = {
        "access_token": token.get("access_token"),
        "userinfo": dict(userinfo),
    }
    
    print("User data to save:", user_data)
    
    # 创建重定向响应并设置 cookie
    response = RedirectResponse("/", status_code=303)
    set_user_cookie(response, user_data)
    
    return response

@app.get("/protected", response_class=HTMLResponse)
async def protected(request: Request):
    if not get_user_from_cookie(request):
        return RedirectResponse("/login")
    return HTMLResponse(
        "<h3>受保护页面</h3>"
        "<p>你已经通过 OIDC 登录，才可以看到这页。</p>"
        '<p><a href="/">返回首页</a></p>'
    )

@app.get("/logout")
async def logout(request: Request):
    # 清除 OAuth session 和用户 cookie
    request.session.clear()
    response = RedirectResponse("/")
    response.delete_cookie("user_data")
    return response
