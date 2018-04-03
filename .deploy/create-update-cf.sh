#!/bin/bash

npm install -g cfn-create-or-update && \
cfn-create-or-update \
    --region ap-southeast-2 \
    --stack-name $STACK_NAME \
    --template-body file://$PWD/cloud-formation.yml \
    --parameters \
        ParameterKey=dockerRepositoryName,ParameterValue=$STACK_NAME