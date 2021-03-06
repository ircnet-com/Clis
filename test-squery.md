# Test
/SQUERY Clis HELP
```
07:16 -Clis(irc.localhost)- Clis help index
07:16 -Clis(irc.localhost)- Use /SQUERY Clis HELP <topic>
07:16 -Clis(irc.localhost)- Available topics: ADMIN, HELP, INFO, LIST, VERSION
```
<br/>

/SQUERY Clis admin
```
07:17 -Clis(irc.localhost)- Administrative info about Clis:
07:17 -Clis(irc.localhost)- IRCnet.com team <info@ircnet.com>
```
<br/>

/SQUERY Clis help admin
```
07:21 -Clis(irc.localhost)- Shows administrative information
```
<br/>

/SQUERY Clis info
```
07:22 -Clis(irc.localhost)- Channel List Service (Clis)
07:22 -Clis(irc.localhost)- For more information visit https://www.ircnet.com
```
<br/>

/SQUERY Clis help info
```
07:22 -Clis(irc.localhost)- Shows information about this software
```
<br/>

/SQUERY Clis version
```
07:23 -Clis(irc.localhost)- Clis v1.0a1
```
<br/>

/SQUERY Clis help version
```
07:24 -Clis(irc.localhost)- Shows the current version
```
<br/>

/SQUERY Clis help foo
```
07:26 -Clis(irc.localhost)- No such help topic: "foo". Use /SQUERY Clis HELP
```
<br/>

/SQUERY Clis help list
```
07:35 -Clis(irc.localhost)- Usage: /SQUERY Clis [options] <mask>
07:35 -Clis(irc.localhost)-  -min <number>        minimum users on a channel
07:35 -Clis(irc.localhost)-  -max <number>        maximum users on a channel
07:35 -Clis(irc.localhost)-  -t,--topic <string>  topic of the channel must contain this string
07:35 -Clis(irc.localhost)-  -s,--show <[m][t]>   show modes (m) and who set the topic (t)
07:35 -Clis(irc.localhost)- For LIST examples use /SQUERY Clis HELP LIST EXAMPLES
```
<br/>

/SQUERY Clis HELP LIST EXAMPLES
```
07:37 -Clis(irc.localhost)- LIST Examples:
07:37 -Clis(irc.localhost)- /SQUERY Clis LIST -min 10 #ircnet*
07:37 -Clis(irc.localhost)-   Lists all channels which start with #ircnet (#ircnet, #ircnet.com, ..) and have at least 10 users
07:37 -Clis(irc.localhost)- /SQUERY Clis LIST * -min 10 -t http
07:37 -Clis(irc.localhost)-   Lists all channels whose topic contains "http" and have at least 10 users
07:37 -Clis(irc.localhost)- /SQUERY Clis LIST * -show mt
07:37 -Clis(irc.localhost)-   Lists all channels and shows the modes and the topic author
```
<br/>

/SQUERY Clis LIST
```
07:38 -Clis(irc.localhost)- You did not specify a channel mask. Use /SQUERY Clis HELP LIST
```
<br/>

/SQUERY Clis LIST *
```
07:42 -Clis(irc.localhost)- Query summary: searching for channels matching "*"
07:42 -Clis(irc.localhost)- Returning a maximum of 60 channel names.
07:42 -Clis(irc.localhost)- #irc                                                1:
07:42 -Clis(irc.localhost)- #test                                               2: this is a test channel
07:42 -Clis(irc.localhost)- &AUTH                                               1: SERVER MESSAGES: messages from the authentication slave
07:42 -Clis(irc.localhost)- &CHANNEL                                            1: SERVER MESSAGES: fake modes
07:42 -Clis(irc.localhost)- &ERRORS                                             1: SERVER MESSAGES: server errors
07:42 -Clis(irc.localhost)- &HASH                                               1: SERVER MESSAGES: hash tables growth
07:42 -Clis(irc.localhost)- &KILLS                                              1: SERVER MESSAGES: operator and server kills
07:42 -Clis(irc.localhost)- &LOCAL                                              1: SERVER MESSAGES: notices about local connections
07:42 -Clis(irc.localhost)- &NOTICES                                            1: SERVER MESSAGES: warnings and notices
07:42 -Clis(irc.localhost)- &NUMERICS                                           1: SERVER MESSAGES: numerics received
07:42 -Clis(irc.localhost)- &SAVE                                               1: SERVER MESSAGES: save messages
07:42 -Clis(irc.localhost)- &SERVERS                                            1: SERVER MESSAGES: servers joining and leaving
07:42 -Clis(irc.localhost)- &SERVICES                                           1: SERVER MESSAGES: services joining and leaving
07:42 -Clis(irc.localhost)- &WALLOPS                                            1: SERVER MESSAGES: wallops received
07:42 -Clis(irc.localhost)- Found 14 visible channels.
```
<br/>

/SQUERY Clis LIST -min 2 *
```
07:45 -Clis(irc.localhost)- Query summary: searching for channels matching "*", minimum 2 users
07:45 -Clis(irc.localhost)- Returning a maximum of 60 channel names.
07:45 -Clis(irc.localhost)- #test                                               2: this is a test channel
07:45 -Clis(irc.localhost)- Found 1 visible channels.
```
<br/>

/SQUERY Clis LIST -min 2 -s mt #test*
```
08:00 -Clis(irc.localhost)- Query summary: searching for channels matching "#test*", minimum 2 users, showing mode, showing who set the topic
08:00 -Clis(irc.localhost)- Returning a maximum of 60 channel names.
08:00 -Clis(irc.localhost)- #test                                              +tn           2: this is a test channel (patrick)
08:00 -Clis(irc.localhost)- Found 1 visible channels.
```
<br/>

/SQUERY Clis LIST -max 1 #*
```
08:30 -Clis(irc.localhost)- Query summary: searching for channels matching "#*", maximum 1 users
08:30 -Clis(irc.localhost)- Returning a maximum of 60 channel names.
08:30 -Clis(irc.localhost)- #irc                                                1:
08:30 -Clis(irc.localhost)- Found 1 visible channels.
```
<br/>

/SQUERY Clis LIST -t errors &*
```
10:12 -Clis(irc.localhost)- Query summary: searching for channels matching "&*", topic containing "errors"
10:12 -Clis(irc.localhost)- Returning a maximum of 60 channel names.
10:12 -Clis(irc.localhost)- &ERRORS                                             1: SERVER MESSAGES: server errors
10:12 -Clis(irc.localhost)- Found 1 visible channels.
```
<br/>

/SQUERY Clis LIST -min foo *
```
10:09 -Clis(irc.localhost)- Argument of -min is not a number: 'foo'
10:09 -Clis(irc.localhost)- Your query contains 1 errors. Use /SQUERY Clis HELP LIST
```
<br/>

/SQUERY Clis LIST -max foo *
```
10:09 -Clis(irc.localhost)- Argument of -max is not a number: 'foo'
10:09 -Clis(irc.localhost)- Your query contains 1 errors. Use /SQUERY Clis HELP LIST
```
<br/>

/SQUERY Clis LIST -show foo *
```
10:10 -Clis(irc.localhost)- Invalid -show flags 'foo'. Allowed flags: 'mt'
10:10 -Clis(irc.localhost)- Your query contains 1 errors. Use /SQUERY Clis HELP LIST
```
<br/>

/SQUERY Clis LIST -t errors
```
10:21 -Clis(irc.localhost)- You did not specify a channel mask. Use /SQUERY Clis HELP LIST
```
