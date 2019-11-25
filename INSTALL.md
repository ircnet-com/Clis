# Install from source
## Prerequisites
* Java 8 or newer
* maven
* Git

## Installation
### irc-library-common
```
$ git clone https://github.com/ircnet-com/irc-library-common.git
$ cd irc-library-common/
$ mvn install
..
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### irc-library-service
```
$ git clone https://github.com/ircnet-com/irc-library-service.git
$ cd irc-library-service/
$ mvn install
..
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Clis
```
$ git clone https://github.com/ircnet-com/Clis.git
```
(no need to install)

## Configuration
### ircd
Add an S:Line to ircd.conf like
```
S:127.0.0.1:password:Clis:0xf000:1
```

```
$ cd Clis/
$ cp src/main/resources/application.properties .
```

Edit application.properties.


## Run Clis
```
$ cd Clis
$ mvn spring-boot:run
(some dependencies will be installed on first run)
[INFO] --- spring-boot-maven-plugin:2.1.6.RELEASE:run (default-cli) @ clis ---
2019-11-25T20:19:16,652 INFO  [main] c.i.s.c.p.f.FilePersistenceServiceImpl: No channels could be loaded from channnels.json
2019-11-25T20:19:16,969 INFO  [Thread-4] c.i.l.c.c.IRCConnection: Connecting to 127.0.0.1 (127.0.0.1) port 6667
2019-11-25T20:19:16,991 INFO  [Thread-4] c.i.l.s.e.ConnectionStatusChangedEventListener: Connection established
2019-11-25T20:19:17,218 INFO  [Thread-4] c.i.s.c.e.YouAreServiceEventListener: Service connected as Clis@irc.localhost
2019-11-25T20:19:17,456 INFO  [Thread-4] c.i.s.c.e.EndOfBurstEventListener: Parsed burst in 0 seconds. Currently 14 channels.
2019-11-25T20:19:17,634 INFO  [Thread-4] c.i.s.c.p.f.FilePersistenceServiceImpl: Saved 14 channels to channnels.json
```

## Test
### IRC
```
/squery Clis list * 
```

### API
http://localhost:8080/
