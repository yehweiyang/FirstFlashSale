pipeline {
    agent any

    tools {
        maven 'Maven 3'  // 跟你剛剛設定的名稱要一致
    }

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
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Post-build') {
            steps {
                echo "✅ Build finished."
            }
        }
    }
}
