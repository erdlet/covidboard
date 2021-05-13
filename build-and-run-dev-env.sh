#! /bin/bash

mvn -B -q clean package

LATEST_IMAGE="erdlet/covistat:latest"
VERSIONED_IMAGE="erdlet/covistat:$(git rev-parse --short HEAD)"

docker build -t $LATEST_IMAGE -t $VERSIONED_IMAGE .

docker-compose --file docker-compose-local.yml up -d