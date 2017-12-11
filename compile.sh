#!/usr/bin/env bash

# mvn -N io.takari:maven:wrapper

mvn -e -DskipTests=true clean package

#mvn  clean   package  -Dmaven.test.skip=true

#docker build -t    wokeboot  .



#docker run -d -p 8080:8080  wokeboot