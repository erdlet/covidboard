#!/usr/bin/env bash

set -e

file_env() {
   local var="$1"
   local fileVar="${var}_FILE"
   local def="${2:-}"

   if [ "${!var:-}" ] && [ "${!fileVar:-}" ]; then
      echo >&2 "error: both $var and $fileVar are set. Using file."
   fi
   local val="$def"
   if [ "${!fileVar:-}" ]; then
      val="$(< "${!fileVar}")"
   elif [ "${!var:-}" ]; then
      val="${!var}"
   fi
   export "$var"="$val"
   unset "$fileVar"
}

file_env "DB_USER"
file_env "DB_PASSWORD"

sh /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0