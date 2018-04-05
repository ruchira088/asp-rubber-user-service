pipeline {
    agent any

    parameters {
        string(name: "DOCKER_REPO", defaultValue: "365562660444.dkr.ecr.ap-southeast-2.amazonaws.com", description: "ECR URI")
    }

    stages {
        stage("Test with coverage") {
            steps {
                script {
                    env.NAME = "$JOB_NAME".replace("/", "-")
                }
                sh "sbt testWithCoverage"
            }
        }

        stage("Create/Update CloudFormation template") {
            steps {
                sh """
                    docker run \
                    -e STACK_NAME=${env.NAME} \
                    -w=/opt \
                    -v "$WORKSPACE/.deploy:/opt" \
                    node bash -c "./create-update-cf.sh"
                    """
            }
        }

        stage("Publish Docker image") {
            steps {

                sh """
                    ./.deploy/deploy-utils.sh && \
                    aws ecr get-login --no-include-email --region ap-southeast-2 | bash && \
                    docker build -t ${env.NAME} -f .deploy/Dockerfile . && \
                    docker tag ${env.NAME}:latest ${params.DOCKER_REPO}/${env.NAME}:latest && \
                    docker push ${params.DOCKER_REPO}/${env.NAME}:latest
                    """
            }
        }
    }

    post {
        success {
            sh "aws s3 cp --recursive s3://secrets.ruchij.com/.kube ~/.kube"
        }
    }
}