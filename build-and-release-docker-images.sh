#!/bin/bash
LATEST_IMAGE="erdlet/covistat:latest"
VERSIONED_IMAGE="erdlet/covistat:$(git rev-parse --short HEAD)"

mvn clean package -B -q

docker build -t $LATEST_IMAGE -t $VERSIONED_IMAGE .

docker push $LATEST_IMAGE
docker push $VERSIONED_IMAGE