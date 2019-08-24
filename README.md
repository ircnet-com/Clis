# About
Clis (Channel list service) is a service to list IRC channels. It replaces the deprecated /LIST command and also offers a REST API. It is basically a remake of [Alis](https://www.ircnet.com/articles/alis) written in Java.

# IRC Commands
Show help:
```
/SQUERY Clis HELP
```
Find all channels:
```
/SQUERY Clis LIST *
```

Find all channels whose user count is at least 100 but not greater than 200:
```
/SQUERY Clis LIST -min 100 -max 200 *
```

Find all channels whose name starts with "irc" and topic contains "http":
```
/SQUERY Clis LIST -t http #irc*
```


# REST API
The REST API can be used by websites, IRC clients and other third party applications to retrieve a list of IRC channels.

## Format
```[ 
{
  "name" : "#irc",
  "topic" : "visit http://www.ircnet.com",
  "topicFrom" : "patrick",
  "modes" : "+nt",
  "userCount" : 42
}, 
{
  "name" : "#ircnet.com",
  "modes" : "+nt",
  "userCount" : 23
}
]
```

## Parameters

| Parameter     | Explanation                                 | Value             | Optional  |
| ------------- |:-------------------------------------------:| ---------------------:|----------:|
| min           | Minimum users                               | Integer               | true      |
| max           | Maximum users                               | Integer               | true      |
| topic         | Topic of the channel must contain this text | String                | true      |
| sortby        | Sort entries by this attribute              | "name" or "usercount" | true      |
| order         | Defines the sort order                      | "asc" or "desc"       | true      |

## Examples
Find all channels:
```
curl -s http://localhost:8080/*
```

Find all channels whose user count is at least 100 but not greater than 200:
```
curl -s "http://localhost:8080/*?min=100&max=200"
```

Find all channels whose name starts with "irc" and topic contains "http":
```
curl -s "http://localhost:8080/#irc*?topic=http"
```

# Installation

1. Add a S line to ircd.conf:
```
S%127.0.0.1%password%Clis%0xf000%1
```
2. Download the JAR
3. Create an application.properties by copying [this example](https://github.com/ircnet-com/Clis/blob/master/src/main/resources/application.properties)
4. Start the application
```
$ java -jar -Dspring.config.location=application.properties clis-1.0-SNAPSHOT.jar 
2019-08-24T04:33:27,191 INFO  [main] c.i.s.c.p.f.FilePersistenceServiceImpl: No channels could be loaded from channnels.json
2019-08-24T04:33:27,515 INFO  [Thread-2] c.i.c.l.c.IRCConnection: Connecting to 127.0.0.1 (127.0.0.1) port 6667
2019-08-24T04:33:27,520 INFO  [Thread-2] c.i.s.l.e.ConnectionStatusChangedEventListener: Connection established
2019-08-24T04:33:31,311 INFO  [Thread-2] c.i.s.c.e.YouAreServiceEventListener: Service connected as Clis@irc.localhost
2019-08-24T04:33:31,553 INFO  [Thread-2] c.i.s.c.e.EndOfBurstEventListener: Parsed burst in 0 seconds. Received 16 channels.
```

# TODO
* Clean up job for obsolete or invisible channels
* Paging for rest api

# Contact
#irc on IRCnet
