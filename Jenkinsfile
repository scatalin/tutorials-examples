
pipeline {
    agent any
    stages {
        stage("Build") {
            agent {
                docker {
                reuseNode true
                image 'maven:3.5.0-jdk-8'
                }
            }
            steps {
                sh 'mvn clean install'
            }
        }
    }
}