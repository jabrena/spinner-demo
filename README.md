# spinner-demo

A demo running multiple tasks

## How to run in local

```
sdk env
mvn clean verify

//ExecutorService
mvn exec:java -Dexec.mainClass="info.jab.demos.SpinnerDemo"

//CompletableFuture
mvn exec:java -Dexec.mainClass="info.jab.demos.SpinnerDemo2"

//Loom
mvn clean package
java --enable-preview -p target/spinner-demo-0.1.0-SNAPSHOT.jar -m info.jab.demos



mvn clean test
```

## Others commands

```
mvn prettier:write
```
