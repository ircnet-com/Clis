FROM maven:3.9.12-eclipse-temurin-21 AS build

ENV JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
WORKDIR /srv

RUN git clone https://github.com/ircnet-com/irc-library-common.git \
 && git clone https://github.com/ircnet-com/irc-library-service.git \
 && git clone https://github.com/ircnet-com/Clis.git

RUN --mount=type=cache,target=/root/.m2 \
    cd /srv/irc-library-common && mvn -B clean install && \
    cd /srv/irc-library-service && mvn -B clean install && \
    cd /srv/Clis && mvn -B clean package && \
    mv target/clis-*.jar /srv/service.jar

FROM eclipse-temurin:21-jre

ENV JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8
WORKDIR /srv

COPY --from=build /srv/service.jar /srv/service.jar

CMD ["java","-jar","/srv/service.jar"]
