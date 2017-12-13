@ECHO OFF

set ARG1=%1
set ARG2=%2

shift
shift

java -Djava.net.useSystemProxies=true -jar neo-1.1.0-SNAPSHOT-jar-with-dependencies.jar %ARG1% %ARG2%
PAUSE