FROM python:3.8-slim

# copy python scripts
COPY . /app
WORKDIR /app/

RUN pip install --upgrade pip \
 && pip install --no-cache-dir -r requirements.txt

# app start script
CMD	[ "uvicorn", "--host", "0.0.0.0", "--port", "8000", "authapi.api:app" ]
