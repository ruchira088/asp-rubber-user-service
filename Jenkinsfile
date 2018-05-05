pipeline {
    agent {
        kubernetes {
            label "jenkins-pod"
            defaultContainer "jnlp"
            yaml """
apiVersion: v1
kind: Pod
spec:
    containers:
        - name: node
          image: node
          tty: true
        - name: openjdk
          image: openjdk:8-jdk
          tty: true
"""
        }
    }

    options {
        disableConcurrentBuilds()
    }

    parameters {
        string(name: "DOCKER_REPO", defaultValue: "365562660444.dkr.ecr.ap-southeast-2.amazonaws.com", description: "ECR URI")
    }

    stages {
        stage("Test with coverage") {
            steps {
                script {
                    env.NAME = "$JOB_NAME".replace("/", "-")
                }

                container("openjdk") {
                    sh """
                        apt-get update && \
                            apt-get install apt-transport-https bc ca-certificates jq awscli software-properties-common -y

                        echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list && \
                            apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
                            apt-get update && \
                            apt-get install sbt -y

                        printenv

                        aws --version
                        aws ssm get-parameter --name github-pat --with-decryption

                        ls -a
                    """
                }
            }
        }

        stage("Create/Update CloudFormation template") {
            steps {
                sh """
                    docker run \
                    -e STACK_NAME=${NAME} \
                    -w=/opt \
                    -v "$WORKSPACE/.deploy:/opt" \
                    node bash -c "./create-update-cf.sh"
                    """
            }
        }

        stage("Publish Docker image") {
            steps {

                sh "./.deploy/publish-docker-image.sh"
            }
        }
    }

    post {
        success {
            sh "aws s3 cp --recursive s3://secrets.ruchij.com/.kube ~/.kube"
        }
    }
}