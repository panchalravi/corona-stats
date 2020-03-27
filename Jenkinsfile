pipeline {
  agent any
  stages {
    stage('Clean') {
      steps {
        sh 'chmod +x ./mvnw && ./mvnw clean'
      }
    }

    stage('Test') {
      steps {
        sh '''echo \'Running test(s)\'
./mvnw test'''
      }
    }

    stage('Package') {
      steps {
        sh '''echo \'Packaging the app\'
./mvnw package'''
      }
    }

    stage('Results') {
      steps {
        junit '**/target/surefire-reports/TEST-*.xml'
      }
    }

  }
}