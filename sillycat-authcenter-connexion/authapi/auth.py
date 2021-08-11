import time
import six
from werkzeug.exceptions import Unauthorized
from jose import JWTError, jwt

JWT_ISSUER = 'sillycat.com'
JWT_SECRET = 'mypassword'
JWT_LIFETIME_SECONDS = 600
JWT_ALGORITHM = 'HS256'


def login(body):
    username = body['username']
    password = body['password']
    if username == 'admin' and password == 'password':
        timestamp = _current_timestamp()
        payload = {
            "iss": JWT_ISSUER,
            "iat": int(timestamp),
            "exp": int(timestamp + JWT_LIFETIME_SECONDS),
            "sub": str(username)
        }
        return jwt.encode(payload, JWT_SECRET, algorithm=JWT_ALGORITHM)
    else:
        return "login failed", 401


def decode_token(token):
    try:
        return jwt.decode(token, JWT_SECRET, algorithms=[JWT_ALGORITHM])
    except JWTError as e:
        six.raise_from(Unauthorized, e)


def _current_timestamp() -> int:
    return int(time.time())