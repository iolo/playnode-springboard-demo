#!/bin/bash

if [ ! -f /tmp/swift2thrift-generator-cli-0.15.1-standalone.jar ]; then
    mvn org.apache.maven.plugins:maven-dependency-plugin:2.8:get \
        -DremoteRepositories=central::default::http://repo1.maven.apache.org/maven2 \
        -Dartifact=com.facebook.swift:swift2thrift-generator-cli:RELEASE:jar:standalone \
        -Ddest=/tmp/
fi

java -cp target/classes:/tmp/swift2thrift-generator-cli-0.15.1-standalone.jar \
    com.facebook.swift.generator.swift2thrift.Main \
    -v \
    -out src/main/thrift/springboard.thrift \
    -namespace java kr.iolo.springboard.thrift \
    -package kr.iolo.springboard.thrift \
    TSpringboard TUser TForum TPost TComment

thrift -v -r -o src/main/node --gen js:node src/main/thrift/springboard.thrift
