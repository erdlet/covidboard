#! /bin/bash

mvn -B -q clean package

sh build-docker-images.sh

docker-compose --file docker-compose-local.yml up -d