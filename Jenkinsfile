pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo "🔁 Cloning source code..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "🔨 Building project with Maven..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Post-build') {
            steps {
                echo "✅ Build finished."
            }
        }
    }
}
