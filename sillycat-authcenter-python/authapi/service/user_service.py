from authapi.model.user_model import User
from authapi.service.jwt_service import JwtService
from authapi.util.log import logger


class UserService:

    def __init__(self):
        self.jwtService = JwtService()

    def register_user(self, user: User):
        logger.info('calling  register_user-------')
        logger.info(user)

    def validate_user(self, user: User):
        logger.info('calling  validate_user-------')
        logger.info(user)
        return self.jwtService.sign_token(user)

    def logout_user(self):
        pass

    def update_user(self):
        pass

    def delete_user(self):
        pass
