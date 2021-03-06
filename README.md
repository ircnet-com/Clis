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

Find all channels whose name starts with "#irc" and topic contains "http":
```
/SQUERY Clis LIST -t http #irc*
```

For more examples read this [test protocol](https://github.com/ircnet-com/Clis/blob/master/test-squery.md).

# REST API
The REST API can be used by websites, IRC clients and other third party applications to retrieve a list of IRC channels. It is based on the format used by [datatables](https://datatables.net/).

## Format
```{
  "draw": 0,
  "recordsTotal": 2,
  "recordsFiltered": 2,
  "data": [
    {
      "name": "#irc",
      "topic": "https://www.ircnet.com | https://www.irc.it",
      "topicFrom": "patrick",
      "modes" : "+mtn",
      "userCount": 42
    },
    {
      "name": "#ircnet.com",
      "topic": "website: https://www.ircnet.com",
      "topicFrom": "patrick",
      "modes" : "+tn",
      "userCount": 23
    }
  ]
}
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
curl -s http://localhost:8080/
```

Find all channels whose user count is at least 100 but not greater than 200:
```
curl -s "http://localhost:8080/?min=100&max=200"
```

Find all channels whose name starts with "#irc" and topic contains "http":
```
curl -s "http://localhost:8080/?name=#irc*&topic=http"
```

For more examples read this [test protocol](https://github.com/ircnet-com/Clis/blob/master/test-rest.md).

# Installation

1. Add a S line to ircd.conf:
```
S%127.0.0.1%password%Clis%0xf000%1
```
2. Download the [JAR](https://github.com/ircnet-com/Clis/releases/download/0.1-alpha/clis-1.0-SNAPSHOT.jar)
3. Create an application.properties file by copying [this example](https://github.com/ircnet-com/Clis/blob/master/src/main/resources/application.properties) in the directory from which the application is run
4. Start the application
```
$ java -jar clis-1.0-SNAPSHOT.jar 
2019-08-24T04:33:27,191 INFO  [main] c.i.s.c.p.f.FilePersistenceServiceImpl: No channels could be loaded from channnels.json
2019-08-24T04:33:27,515 INFO  [Thread-2] c.i.c.l.c.IRCConnection: Connecting to 127.0.0.1 (127.0.0.1) port 6667
2019-08-24T04:33:27,520 INFO  [Thread-2] c.i.s.l.e.ConnectionStatusChangedEventListener: Connection established
2019-08-24T04:33:31,311 INFO  [Thread-2] c.i.s.c.e.YouAreServiceEventListener: Service connected as Clis@irc.localhost
2019-08-24T04:33:31,553 INFO  [Thread-2] c.i.s.c.e.EndOfBurstEventListener: Parsed burst in 0 seconds. Received 16 channels.
```

# TODO
* Update API documentation

# Contact
#irc on IRCnet
