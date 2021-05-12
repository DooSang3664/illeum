FROM openjdk:8-jdk-alpine
# ARG JAR_FILE=./target/*.jar
# COPY ${JAR_FILE} app.jar
# ADD ./target/basePJT-0.0.1-SNAPSHOT.jar app.jar

COPY ./volumes /volumes

RUN apk --update add tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    apk del tzdata && \
    rm -rf /var/cache/apk/*

ADD ./target/auth-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""

# ARG DEBIAN_FRONTEND=noninteractive
# ENV TZ=Asia/Seoul

ENTRYPOINT ["java","-jar","/app.jar"]
