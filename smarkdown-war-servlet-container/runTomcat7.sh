#!/bin/bash
#
# Starts Tomcat7 on port 9000
#
mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run \
	-Dcargo.maven.containerId=tomcat7x \
	-Dcargo.servlet.port=9000 \
	-Dcargo.maven.containerUrl=http://archive.apache.org/dist/tomcat/tomcat-7/v7.0.54/bin/apache-tomcat-7.0.54.zip
	-DskipTests
