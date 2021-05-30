IMAGE=sillycat.authapi
TAG=latest
NAME=sillycat.authapi
REPOSITORY=docker.io
STAGE=stage

docker-context:

build: docker-context
		docker build --tag $(REPOSITORY)/$(IMAGE):$(TAG) --build-arg STAGE=${STAGE} .

run:
		docker run -d -p 8000:8000 --link postgres:postgres -e RUNNING_ENV=dev --name $(NAME) $(REPOSITORY)/$(IMAGE):$(TAG)

run-db:
		docker run --name postgres -d -p 5432:5432 -e POSTGRES_PASSWORD=123456 postgres

enter-db:
		docker exec -it postgres psql -U postgres -d postgres

clean:
		docker stop $(NAME)
		docker rm $(NAME)

clean-db:
		docker stop postgres
		docker rm postgres

log:
		docker logs -t -f $(NAME)
