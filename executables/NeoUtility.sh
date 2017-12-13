#!/usr/bin/env bash

java -Djava.net.useSystemProxies=true -jar neo-1.1.0-SNAPSHOT-jar-with-dependencies.jar $1 $2

read -p "Press any key to continue... " -n1 -s
