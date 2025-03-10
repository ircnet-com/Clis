FROM maven:3.8.5-openjdk-17

ENV JAVA_TOOL_OPTIONS=-Dfile.encoding=UTF8

WORKDIR /srv

RUN git clone https://github.com/ircnet-com/irc-library-common.git \
 && git clone https://github.com/ircnet-com/irc-library-service.git \
 && git clone https://github.com/ircnet-com/Clis.git

RUN cd irc-library-common/ \
 && mvn install \
 && mvn clean

RUN cd irc-library-service/ \
 && mvn install \
 && mvn clean

RUN cd Clis \
 && mvn package \
 && mv target/clis-*.jar /srv/clis.jar

RUN rm -rf irc-library-common irc-library-service Clis

CMD ["java","-jar","/srv/clis.jar"]
