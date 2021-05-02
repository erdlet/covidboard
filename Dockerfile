FROM jboss/wildfly:23.0.1.Final

ADD wildfly-configs/modules /opt/jboss/wildfly/modules/
ADD wildfly-configs/standalone.xml /opt/jboss/wildfly/standalone/configuration/
ADD target/covistat.war /opt/jboss/wildfly/standalone/deployments/