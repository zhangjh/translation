#!/bin/bash

#git pull
#ps -ef | grep spring | grep -v grep | awk '{print $2}' | xargs kill

mvn clean package -DskipTests && mvn spring-boot:run &> app.log
