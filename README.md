[User Guide](https://netty.io/wiki/user-guide-for-4.x.html#wiki-h3-8)
## DiscardServer
After server was started, run `telnet 127.0.0.1 8080` and enter message you will find it in your console.

## TimeServer && TimeClient

When TimeServer receives a request from TimeClient, it will send a UnixTime which carries a time message to client. After client receives it, client will print it.