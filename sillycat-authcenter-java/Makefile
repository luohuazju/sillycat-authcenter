TAG=1.0.0
NAME=sillycat-authcenter-java

app-build:
	mvn clean package -Dmaven.test.skip

app-run:
	java -Djava.net.preferIPv4Stack=true \
	 -Dspring.profiles.active=stage \
	 -Dserver.address=localhost \
	 -XX:MaxMetaspaceSize=256M -XX:+HeapDumpOnOutOfMemoryError \
	 -XX:HeapDumpPath="/tmp/dump_oom.hprof" \
	 -jar ./target/sillycat-authcenter-java-*.jar

build:
	docker build -f Dockerfile -t $(NAME):$(TAG) .

run:
	docker run -d -p 9527:9527 --name $(NAME) $(NAME):$(TAG)
	
debug:
	docker run -ti -p 9527:9527 --name $(NAME) $(NAME):$(TAG) /bin/bash

clean:
	docker stop $(NAME)
	docker rm $(NAME)