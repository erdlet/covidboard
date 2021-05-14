FROM erdlet/wildfly-mariadb:2301-272

COPY target/covistat.war /opt/jboss/wildfly/standalone/deployments/ROOT.war
COPY docker-entrypoint.sh /usr/local/bin/
USER root
RUN chmod +x /usr/local/bin/docker-entrypoint.sh
USER jboss

ENV TZ="Europe/Berlin"

ENTRYPOINT ["docker-entrypoint.sh"]