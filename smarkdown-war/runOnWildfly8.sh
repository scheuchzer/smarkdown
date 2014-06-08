#!/bin/bash
#
# Starts Glassfish on port 9000
#
# URL: http://localhost:9000/smarkdown/
#
mvn clean verify org.codehaus.cargo:cargo-maven2-plugin:run \
	-Dcargo.maven.containerId=wildfly8x \
	-Dcargo.maven.containerUrl=http://download.jboss.org/wildfly/8.1.0.Final/wildfly-8.1.0.Final.zip \
	-Dcargo.servlet.port=9000 -DskipTests
