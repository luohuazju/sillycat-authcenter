IMAGE=sillycat-authcenter-nodejs
TAG=1.0
NAME=sillycat-authcenter-nodejs
REPOSITORY=docker.io

docker-context:

build: docker-context
	docker build -t $(REPOSITORY)/$(IMAGE):$(TAG) .

run-app:
	npm start

run:
	docker run -d -p 8021:8088 -e NODE_ENV=production -v /etc/hosts:/etc/hosts --name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG)

debug:
	docker run -ti -p 8021:8088 -e NODE_ENV=development -v /etc/hosts:/etc/hosts --name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG) /bin/bash

clean:
	docker stop ${NAME}
	docker rm ${NAME}

logs:
	docker logs ${NAME}

publish:
	docker push  $(REPOSITORY)/$(IMAGE):$(TAG) 
