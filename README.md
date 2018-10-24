# kafka-adminclient-test

Simple test of Kafka AdminClient (create topic)

Run with:
```
mvn compile exec:java -Dexec.args="bootstrap-string topic-name" -Djava.security.auth.login.config=jaas-config-file
```
