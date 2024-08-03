## API DOC
with Swagger  
- [Local Swagger](localhost:18080/api/pub/swagger-ui/index.html)

### [Security]
- https://medium.com/spring-boot/spring-boot-3-spring-security-6-jwt-authentication-authorization-98702d6313a5
- 


## Kafka
카프카 가이드

### 1. Install
설치 방법
```shell
# on macos
brew install kafka
```

#### 2. Server Start
설정 파일들이 아래 세팅된 것을 확인
```shell
$ ls /opt/homebrew/etc/kafka/
connect-console-sink.properties   connect-file-source.properties    consumer.properties               server.properties
connect-console-source.properties connect-log4j.properties          kraft                             tools-log4j.properties
connect-distributed.properties    connect-mirror-maker.properties   log4j.properties                  trogdor.conf
connect-file-sink.properties      connect-standalone.properties     producer.properties               zookeeper.properties

```
서버 시작을 위한 command 가이드
```bash
# To start kafka now and restart at login: brew services start kafka
CONFIG_BASE=/opt/homebrew/etc/kafka
ZOO_CONF="$CONFIG_BASE/zookeeper.properties"
KAFAKA_CONF="$CONFIG_BASE/server.properties"

/opt/homebrew/opt/kafka/bin/zookeeper-server-start $ZOO_CONF
/opt/homebrew/opt/kafka/bin/kafka-server-start $KAFAKA_CONF
```


