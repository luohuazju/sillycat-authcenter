FROM python:3.8-slim

RUN  mkdir -p /usr/src/app/
WORKDIR /usr/src/app/

# prepre libraries
COPY requirements.txt ./
RUN pip install --upgrade pip \
 && pip install --no-cache-dir -r requirements.txt


# copy python scripts
COPY . /usr/src/app/

# app start script
CMD	[ "uvicorn", "--host", "0.0.0.0", "--port", "8000", "authapi.api:app" ]
