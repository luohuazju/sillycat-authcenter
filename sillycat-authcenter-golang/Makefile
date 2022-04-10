IMAGE=sillycat.authapi.golang
TAG=latest
NAME=sillycat.authapi.golang
REPOSITORY=docker.io
STAGE=stage

docker-context:

build: docker-context
		docker build --tag $(REPOSITORY)/$(IMAGE):$(TAG) --build-arg STAGE=${STAGE} .

run:
		docker run -d -p 8022:8080 -e ENVIROMENT=PROD --name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG)

clean:
		docker stop $(NAME)
		docker rm $(NAME)

log:
		docker logs -t -f $(NAME)
