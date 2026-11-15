pipeline {
    agent any

    options {
        timestamps()
        skipDefaultCheckout()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B -ntp clean test -Dbrowser.headless=true'
                    } else {
                        bat 'mvn -B -ntp clean test -Dbrowser.headless=true'
                    }
                }
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/extent-report.html,target/screenshots/**',
                             allowEmptyArchive: true
        }
    }
}
