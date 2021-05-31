from authapi.model.user_model import User, LoginUser
from authapi.service.jwt_service import JwtService
from authapi.util.log import logger
from authapi.dao.user_dao import UserDAO
from authapi.exception.authapi_exception import AuthAPIException


class UserService:

    def __init__(self):
        self.jwtService = JwtService()
        self.userDAO = UserDAO()

    def register_user(self, user: User):
        logger.info('calling  register_user-------')
        logger.info(user)
        exist = self.userDAO.get_user(user.email)
        if exist:
            logger.info('email is already used', user.email)
            raise AuthAPIException('email_exist', 'email already exist')
        self.userDAO.create_user(user)

    def get_user(self, email):
        logger.info('calling get_user ------')
        logger.info(email)
        return self.userDAO.get_user(email)

    def validate_user(self, user: LoginUser):
        logger.info('calling  validate_user-------')
        logger.info(user)
        exist = self.userDAO.get_user(user.email)
        if exist and exist.password == user.password:
            return self.jwtService.sign_token(exist)
        raise AuthAPIException('email_passport_wrong', 'email or password is wrong')

    def logout_user(self, token, user):
        # TODO save the invalid token in DB/Cache for 30 minutes
        pass

    def update_user(self, user):
        logger.info('calling  update_user-------')
        logger.info(user)
        exist = self.userDAO.get_user(user.email)
        if exist:
            exist.name = user.name
            exist.password = user.password
            return self.userDAO.update_user(exist)
        raise AuthAPIException('user_not_exist', 'user not exist')

    def delete_user(self, email):
        logger.info('calling delete_user--------')
        logger.info(email)
        # TODO save the invalid email in DB/Cache for 30 minutes
        self.userDAO.delete_user(email)
