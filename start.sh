#!/bin/bash

ps -ef | grep spring | grep -v grep | awk '{print $2}' | xargs kill

mvn clean package -DskipTests && nohup mvn spring-boot:run &