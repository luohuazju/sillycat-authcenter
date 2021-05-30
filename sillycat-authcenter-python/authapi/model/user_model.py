from pydantic import BaseModel
from typing import Optional
from datetime import datetime


class UserRequest(BaseModel):
    name: str
    email: str
    password: str
    confirmPassword: str


class User(BaseModel):
    name: str
    email: str
    password: str
    changedBy: Optional[str]
    updatedAt: Optional[datetime]
