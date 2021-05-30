from authapi.service.user_service import UserService
from authapi.model.user_model import User
from authapi.util.log import logger
from fastapi import APIRouter, FastAPI, Header


router = APIRouter()
user_service = UserService()


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
    return {"status": 'OK'}


@router.delete("/users")
async def delete_user(x_token: str = Header(None)):
    logger.info('received delete user request request-------')
    logger.info(x_token)
    return {"status": 'OK'}


@router.post("/login")
async def login(user: User):
    logger.info('received login post request request-------')
    logger.info(user)
    token = user_service.validate_user(user)
    return {"status": 'OK', "token": token}


@router.get("/logout")
async def logout(x_token: str = Header(None)):
    logger.info('received logout post request request-------')
    logger.info(x_token)
    return {"status": 'OK'}


app = FastAPI()
app.include_router(router, prefix="/api/v1")