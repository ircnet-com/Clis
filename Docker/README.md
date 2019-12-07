# Building Clis for Docker
In the current directory exists a Dockerfile using [maven:ibmjava-alpine](https://hub.docker.com/_/maven) as the base. 

This is the configuration used by hub.uk on [IRCnet](https://www.ircnet.com).

## Building
    git clone
    cd Clis
    docker build -t clis Docker/

## Configuration
You'll need to make a configuration file based on ./src/main/resources/application.properties

## Running
You can run in a few ways. The most simple of which is;
    docker run -d -v /path/to/application.properties:/srv/application.properties clis:latest

You can then expose the HTTP Port for API requests like so;
    docker run -d -v /path/to/application.properties:/srv/application.properties -p 8080:8080 clis:latest

You can also chose to make the channels.json file persistent also;
    docker run -d -v /path/to/application.properties:/srv/application.properties -p 8080:8080 -v /path/to/channels.json:/srv/channels.json clis:latest
