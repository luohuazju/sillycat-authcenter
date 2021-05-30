from authapi.service.user_service import UserService
from authapi.model.user_model import UserRequest
from fastapi import FastAPI
from fastapi import APIRouter
from authapi.util.log import logger


router = APIRouter()
user_service = UserService()


@router.get("/health")
async def health():
    logger.info('enter health check!')
    return {"status": 'OK'}


@router.post("/users")
async def register_user(request: UserRequest):
    logger.info('received user post request request-------')
    logger.info(request)
    return {"status": 'OK'}


app = FastAPI()
app.include_router(router, prefix="/api/v1")