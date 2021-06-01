from authapi.service.user_service import UserService, JwtService
from authapi.model.user_model import User, LoginUser
from authapi.util.log import logger
from fastapi import APIRouter, FastAPI, Header, HTTPException, Request
from fastapi.responses import JSONResponse
from authapi.exception.authapi_exception import AuthAPIException

router = APIRouter()
user_service = UserService()
jwt_service = JwtService()


@router.get("/health")
async def health():
    logger.info('enter health check!')
    return {"status": 'OK'}


@router.post("/users")
async def register_user(user: User):
    logger.info('received user post request request-------')
    logger.info(user)
    user_service.register_user(user)
    return {"status": 'OK'}


@router.put("/users")
async def update_user(user: User, x_token: str = Header(None)):
    logger.info('received put user request request-------')
    logger.info(user)
    logger.info(x_token)
    decoded = jwt_service.verify_token(x_token)
    if decoded:
        user_service.update_user(user)
        return {"status": 'OK'}
    else:
        raise HTTPException(status_code=403, detail="Permission not allowed")


@router.delete("/users")
async def delete_user(x_token: str = Header(None)):
    logger.info('received delete user request request-------')
    logger.info(x_token)
    decoded = jwt_service.verify_token(x_token)
    if decoded:
        user_service.delete_user(decoded['email'])
        return {"status": 'OK'}
    else:
        raise HTTPException(status_code=403, detail="Permission not allowed")


@router.get("/users")
async def get(x_token: str = Header(None)):
    logger.info('received get user request request-------')
    logger.info(x_token)
    decoded = jwt_service.verify_token(x_token)
    if decoded:
        logger.info(decoded)
        user = user_service.get_user(decoded['email'])
        return {"status": 'OK', "user": user}
    else:
        raise HTTPException(status_code=403, detail="Permission not allowed")


@router.post("/login")
async def login(user: LoginUser):
    logger.info('received login post request request-------')
    logger.info(user)
    token = user_service.validate_user(user)
    return {"status": 'OK', "token": token}


@router.get("/logout")
async def logout(x_token: str = Header(None)):
    logger.info('received logout post request request-------')
    logger.info(x_token)
    decoded = jwt_service.verify_token(x_token)
    if decoded:
        user_service.logout_user(x_token, decoded)
        return {"status": 'OK'}
    else:
        raise HTTPException(status_code=403, detail="Permission not allowed")


app = FastAPI()
app.include_router(router, prefix="/api/v1")


@app.exception_handler(AuthAPIException)
async def unicorn_exception_handler(request: Request, exc: AuthAPIException):
    return JSONResponse(
        status_code=400,
        content={"message": f"{exc.msg}"},
    )
