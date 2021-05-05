FROM erdlet/wildfly-mariadb:2301-272

ADD target/covistat.war /opt/jboss/wildfly/standalone/deployments/