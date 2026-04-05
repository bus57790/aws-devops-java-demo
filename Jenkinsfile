pipeline {
    agent any

    environment {
        APP_NAME = "aws-devops-demo"
        DOCKER_IMAGE = "bus57790/aws-devops-demo"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    tools {
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/bus57790/aws-devops-java-demo.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                       mvn sonar:sonar \
                       -Dsonar.projectKey=aws-devops-demo \
                       -Dsonar.projectName=aws-devops-demo
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
                sh 'docker tag $DOCKER_IMAGE:$DOCKER_TAG $DOCKER_IMAGE:latest'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                        docker push $DOCKER_IMAGE:$DOCKER_TAG
                        docker push $DOCKER_IMAGE:latest
                    '''
                }
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                    docker rm -f aws-devops-demo || true
                    docker run -d --name aws-devops-demo -p 8081:8081 $DOCKER_IMAGE:latest
                '''
            }
        }
    }

    post {
        always {
            sh 'docker image prune -f || true'
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed.'
        }
    }
}
