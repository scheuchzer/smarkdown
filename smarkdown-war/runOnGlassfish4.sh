#!/bin/bash
#
# Starts Glassfish on port 9000
#
mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run \
	-Dcargo.maven.containerId=glassfish4x \
	-Dcargo.maven.containerUrl=http://dlc.sun.com.edgesuite.net/glassfish/4.0.1/promoted/latest-glassfish.zip \
	-Dcargo.servlet.port=9000 \
	-DskipTests
