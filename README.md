# Micronaut 1.2.0 / PoC of a memory leak 

This application reproduces a memory leak when controller's POST method handler does not have @Body as a parameter.
The application is run as a JDK11 Docker-container, `io.netty.leakDetection.level` set to `paranoid`. 

## How to reproduce

### Build and run application
Build a Docker image:
```
./gradlew buildDocker
``` 

Start it using docker-compose:
```
docker-compose up
``` 

### Reproduce leak
Run script `post_form_data_ok.sh` 
```
./scripts/post_form_data_ok.sh
```

This should reproduce following output:
```
...
micronaut-form-post | 09:56:05.812 INFO  e.micronaut.PostFormLeakController - createUrlEncoded, body: '[password:secret]'
micronaut-form-post | 09:56:05.827 INFO  e.micronaut.PostFormLeakController - createUrlEncoded, body: '[password:secret]'
micronaut-form-post | 09:56:05.844 INFO  e.micronaut.PostFormLeakController - createUrlEncoded, body: '[password:secret]'
micronaut-form-post | 09:56:05.860 INFO  e.micronaut.PostFormLeakController - createUrlEncoded, body: '[password:secret]'
...
```


Run script `post_form_data_ok.sh` 
```
./scripts/post_form_data_ok.sh
```

This should reproduce following output:
```
...
memory-leaker    | 10:49:31.447 [nioEventLoopGroup-1-4] ERROR io.netty.util.ResourceLeakDetector - LEAK: ByteBuf.release() was not called before it's garbage-collected. See https://netty.io/wiki/reference-counted-objects.html for more information.
memory-leaker    | Recent access records: 
memory-leaker    | #1:
memory-leaker    |      Hint: 'http-streams-codec-body-publisher' will handle the message from this point.
memory-leaker    |      io.netty.handler.codec.http.DefaultHttpContent.touch(DefaultHttpContent.java:88)
memory-leaker    |      io.netty.handler.codec.http.DefaultLastHttpContent.touch(DefaultLastHttpContent.java:88)
...
```
Full example of access records here: [error.log](./error.log)
