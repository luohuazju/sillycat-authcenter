IMAGE=sillycat.authapi
TAG=latest
NAME=sillycat.authapi
REPOSITORY=docker.io
STAGE=stage

docker-context:

build: docker-context
		docker build --tag $(REPOSITORY)/$(IMAGE):$(TAG) --build-arg STAGE=${STAGE} .

run:
		docker run -d -p 8000:8000 -e RUNNING_ENV=dev --name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG)

clean:
		docker stop $(NAME)
		docker rm $(NAME)

log:
		docker logs -t -f $(NAME)
