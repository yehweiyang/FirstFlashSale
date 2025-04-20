pipeline {
    agent any

    tools {
        jdk 'JDK 17'      // ← 這名字跟你剛剛 Global Tool 裡填的一樣
        maven 'Maven 3'   // ← 如果你用了 Jenkins 自動裝 Maven
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
