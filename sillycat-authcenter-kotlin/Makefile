TAG=1.0.0
NAME=sillycat-kotlinstarter

docker-context:

build:
	docker build -f Dockerfile -t $(NAME):$(TAG) .

app-build:
	gradle build

app-run:
	java -Djava.net.preferIPv4Stack=true \
	 -Dspring.profiles.active=dev \
	 -jar ./build/libs/sillycat-authcenter-kotlin-1.0.0.jar

run:
	docker run -d -p 8001:8001 --name $(NAME) $(NAME):$(TAG)

run-prod:
	docker run -d -p 8025:9000 --env RUN_ENV=prod --name $(NAME) $(NAME):$(TAG)

clean:
	docker stop $(NAME)
	docker rm $(NAME)

