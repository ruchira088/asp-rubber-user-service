#!/bin/bash

git clone https://github.com/ruchira088/deployment-utils.git
docker build -t deployment-utils -f deployment-utils/Dockerfile ./deployment-utils
rm -rf deployment-utils

docker run \
    -v "$PWD/.deploy:/opt/deployment-utils/output" \
    deployment-utils \
    --command docker-image-version-tag --repositoryName ${NAME}

versionTag="v$((`cat .deploy/docker-version.txt` + 1))"

aws ecr get-login --no-include-email --region ap-southeast-2 | bash
docker build -t ${NAME} -f .deploy/Dockerfile .
docker tag ${NAME}:latest ${params.DOCKER_REPO}/${NAME}:$versionTag
docker push ${params.DOCKER_REPO}/${NAME}:$versionTag