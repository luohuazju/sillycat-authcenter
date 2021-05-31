from pydantic import BaseModel
from typing import Optional


class User(BaseModel):
    id: Optional[int]
    name: str
    email: str
    password: str


class LoginUser(BaseModel):
    email: str
    password: str
