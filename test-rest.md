# ! THIS DOCUMENT IS OUTDATED AND WILL BE UPDATED SOON !

$ curl -s http://localhost:8080/
```
{
  "draw" : 0,
  "recordsTotal" : 14,
  "recordsFiltered" : 14,
  "data" : [ {
    "name" : "#irc",
    "topic" : "https://www.ircnet.com | https://www.irc.it",
    "topicFrom" : null,
    "modes" : "+mtn",
    "userCount" : 4
  }, {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  }, {
    "name" : "&AUTH",
    "topic" : "SERVER MESSAGES: messages from the authentication slave",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&CHANNEL",
    "topic" : "SERVER MESSAGES: fake modes",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&ERRORS",
    "topic" : "SERVER MESSAGES: server errors",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&HASH",
    "topic" : "SERVER MESSAGES: hash tables growth",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&KILLS",
    "topic" : "SERVER MESSAGES: operator and server kills",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&LOCAL",
    "topic" : "SERVER MESSAGES: notices about local connections",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&NOTICES",
    "topic" : "SERVER MESSAGES: warnings and notices",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&NUMERICS",
    "topic" : "SERVER MESSAGES: numerics received",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&SAVE",
    "topic" : "SERVER MESSAGES: save messages",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&SERVERS",
    "topic" : "SERVER MESSAGES: servers joining and leaving",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&SERVICES",
    "topic" : "SERVER MESSAGES: services joining and leaving",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  }, {
    "name" : "&WALLOPS",
    "topic" : "SERVER MESSAGES: wallops received",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?min=2"
```
{
  "draw" : 0,
  "recordsTotal" : 2,
  "recordsFiltered" : 2,
  "data" : [ {
    "name" : "#irc",
    "topic" : "https://www.ircnet.com | https://www.irc.it",
    "topicFrom" : null,
    "modes" : "+mtn",
    "userCount" : 4
  }, {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?min=3&max=3"


```
{
  "draw" : 0,
  "recordsTotal" : 1,
  "recordsFiltered" : 1,
  "data" : [ {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?topic=error"

```
{
  "draw" : 0,
  "recordsTotal" : 1,
  "recordsFiltered" : 1,
  "data" : [ {
    "name" : "&ERRORS",
    "topic" : "SERVER MESSAGES: server errors",
    "topicFrom" : null,
    "modes" : "+mtnaq",
    "userCount" : 1
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?min=2&sortby=userCount&order=desc"

```
{
  "draw" : 0,
  "recordsTotal" : 2,
  "recordsFiltered" : 2,
  "data" : [ {
    "name" : "#irc",
    "topic" : "https://www.ircnet.com | https://www.irc.it",
    "topicFrom" : null,
    "modes" : "+mtn",
    "userCount" : 4
  }, {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?min=2&sortby=userCount&order=asc"

```
{
  "draw" : 0,
  "recordsTotal" : 2,
  "recordsFiltered" : 2,
  "data" : [ {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  }, {
    "name" : "#irc",
    "topic" : "https://www.ircnet.com | https://www.irc.it",
    "topicFrom" : null,
    "modes" : "+mtn",
    "userCount" : 4
  } ]
}
```

<br/>

$ curl -s "http://localhost:8080/?min=2&sortby=name&order=asc"

```
{
  "draw" : 0,
  "recordsTotal" : 2,
  "recordsFiltered" : 2,
  "data" : [ {
    "name" : "#irc",
    "topic" : "https://www.ircnet.com | https://www.irc.it",
    "topicFrom" : null,
    "modes" : "+mtn",
    "userCount" : 4
  }, {
    "name" : "#ircnet.com",
    "topic" : "website: https://www.ircnet.com",
    "topicFrom" : null,
    "modes" : "+tn",
    "userCount" : 3
  } ]
```
