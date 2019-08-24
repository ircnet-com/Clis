$ curl -s http://localhost:8080/*
```
[ {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 1
}, {
  "name" : "#test",
  "topic" : "this is a test channel",
  "topicFrom" : "patrick",
  "modes" : "+tn",
  "userCount" : 3
}, {
  "name" : "&AUTH",
  "topic" : "SERVER MESSAGES: messages from the authentication slave",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&CHANNEL",
  "topic" : "SERVER MESSAGES: fake modes",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&ERRORS",
  "topic" : "SERVER MESSAGES: server errors",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&HASH",
  "topic" : "SERVER MESSAGES: hash tables growth",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&KILLS",
  "topic" : "SERVER MESSAGES: operator and server kills",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&LOCAL",
  "topic" : "SERVER MESSAGES: notices about local connections",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&NOTICES",
  "topic" : "SERVER MESSAGES: warnings and notices",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&NUMERICS",
  "topic" : "SERVER MESSAGES: numerics received",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&SAVE",
  "topic" : "SERVER MESSAGES: save messages",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&SERVERS",
  "topic" : "SERVER MESSAGES: servers joining and leaving",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&SERVICES",
  "topic" : "SERVER MESSAGES: services joining and leaving",
  "modes" : "+mtnaq",
  "userCount" : 1
}, {
  "name" : "&WALLOPS",
  "topic" : "SERVER MESSAGES: wallops received",
  "modes" : "+mtnaq",
  "userCount" : 1
} ]
```

$ curl -s "http://localhost:8080/*?min=2"
```
[ {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 2
}, {
  "name" : "#test",
  "topic" : "this is a test channel",
  "topicFrom" : "patrick",
  "modes" : "+tn",
  "userCount" : 3
} ]
```

$ curl -s "http://localhost:8080/*?min=2&max=2"
```
[ {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 2
} ]
```

$ curl -s "http://localhost:8080/&*?topic=error"
```
[ {
  "name" : "&ERRORS",
  "topic" : "SERVER MESSAGES: server errors",
  "modes" : "+mtnaq",
  "userCount" : 1
} ]
```

$ curl -s "http://localhost:8080/*?min=2&sortBy=userCount&order=desc"
```
[ {
  "name" : "#test",
  "topic" : "this is a test channel",
  "topicFrom" : "patrick",
  "modes" : "+tn",
  "userCount" : 3
}, {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 2
} ]
```

$ curl -s "http://localhost:8080/*?min=2&sortBy=userCount&order=asc"
```
[ {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 2
}, {
  "name" : "#test",
  "topic" : "this is a test channel",
  "topicFrom" : "patrick",
  "modes" : "+tn",
  "userCount" : 3
} ]
```

$ curl -s "http://localhost:8080/*?min=2&sortBy=name&order=asc"
```
[ {
  "name" : "#irc",
  "modes" : "+",
  "userCount" : 2
}, {
  "name" : "#test",
  "topic" : "this is a test channel",
  "topicFrom" : "patrick",
  "modes" : "+tn",
  "userCount" : 3
} ]
```
