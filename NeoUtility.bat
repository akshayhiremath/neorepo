@ECHO OFF

set ARG1=%1
set ARG2=%2

shift
shift

java -cp .;lib/jackson-annotations-2.0.2.jar;lib/jackson-core-2.0.2.jar;lib/jackson-databind-2.0.2.jar;lib/jsr311-api-1.1.1.jar;lib/jackson-core-asl-1.9.2.jar;lib/jackson-xc-1.9.2.jar;lib/jackson-mapper-asl-1.9.2.jar;lib/jackson-jaxrs-1.9.2.jar;lib/log4j-1.2.17.jar;lib/javax.ws.rs-api-2.0.1.jar;lib/jersey-core-1.19.4.jar;lib/jersey-client-1.19.4.jar;lib/gson-2.6.2.jar;target/neo-1.0.0-SNAPSHOT.jar com.akshay.client.neo.Main %ARG1% %ARG2%
PAUSE