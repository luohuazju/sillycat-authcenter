FROM maven:3.8.5-openjdk-17 AS build
     
LABEL maintainer="Carl Luo <luohuazju@gmail.com>"

COPY . /app/sillycat-authcenter-java
WORKDIR /app/sillycat-authcenter-java
RUN mvn clean package -Dmaven.test.skip

FROM gcr.io/distroless/java17-debian11
COPY --from=build /app/sillycat-authcenter-java/target/sillycat-authcenter-java-*.jar /app/sillycat-authcenter-java.jar
WORKDIR /app
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-Dserver.address=0.0.0.0", "-Dspring.profiles.active=stage", "-XX:MaxMetaspaceSize=256M", "-XX:+HeapDumpOnOutOfMemoryError", "-jar",  "./sillycat-authcenter-java.jar" ]
