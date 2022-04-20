FROM gradle:7.4-jdk17 AS build

LABEL maintainer="Yiyi Kang <yiyikangrachel@gmail.com>"

COPY . /app/sillycat-authcenter
WORKDIR /app/sillycat-authcenter
RUN gradle build

FROM gcr.io/distroless/java17-debian11
COPY --from=build /app/sillycat-authcenter/build/libs/sillycat-authcenter-kotlin-1.0.0.jar /app/sillycat-authcenter.jar
WORKDIR /app
ENTRYPOINT ["java", "-Djava.net.preferIPv4Stack=true", "-Dspring.profiles.active=${RUN_ENV:dev}", "-XX:MaxMetaspaceSize=256M", "-jar",  "./sillycat-authcenter.jar" ]
