pipeline {
  agent any
  stages {
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            isUnix()
          }
        }
        stage('') {
          steps {
            sh 'mvn clean install'
          }
        }
      }
    }
  }
}