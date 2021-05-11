#!/bin/bash
LATEST_IMAGE="erdlet/covistat:latest"
VERSIONED_IMAGE="erdlet/covistat:$(git rev-parse --short HEAD)"

docker build -t $LATEST_IMAGE -t $VERSIONED_IMAGE .