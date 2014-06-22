#!/bin/bash
#
# Starts Tomcat8 on port 9000
#
mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run \
	-Dcargo.maven.containerId=tomcat8x \
	-Dcargo.servlet.port=9000 \
	-Dcargo.maven.containerUrl=http://archive.apache.org/dist/tomcat/tomcat-8/v8.0.8/bin/apache-tomcat-8.0.8.zip
	-DskipTests
