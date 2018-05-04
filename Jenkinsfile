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
        - name: ubuntu
          image: ubuntu
          tty: true
        - name: java
          image: openjdk
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

                container("ubuntu") {
                    sh """
                        echo 'Hello World'
                        echo 'Foobar'
                        """
                }

                container("java") {
                    sh "java -version"
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