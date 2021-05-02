FROM jboss/wildfly:23.0.1.Final

ADD target/covistat.war /opt/jboss/wildfly/standalone/deployments/