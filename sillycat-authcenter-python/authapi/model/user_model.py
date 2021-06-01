from pydantic import BaseModel
from typing import Optional
from datetime import datetime


class User(BaseModel):
    id: Optional[int]
    name: str
    email: str
    password: str


class UserToken(BaseModel):
    name: str
    email: str


class LoginUser(BaseModel):
    email: str
    password: str
