 pipeline {
    agent any



    stages {
        stage('Checkout Git') {
            steps {
                script {
                    echo 'Pulling...'
                    git branch: 'Firass', url: 'https://github.com/raed717/Devops_Bidbound.git'
                }
            }
        }
        stage('Check Maven Version') {
            steps {
                script {
                    def mavenVersion = sh(script: 'mvn --version', returnStatus: true)
                    if (mavenVersion == 0) {
                        echo 'Maven is installed. Here is the version:'
                        sh 'mvn -version'
                    } else {
                        error 'Maven is not installed.'
                    }
                }
            }
        }
    stage('Compile') {
           steps {
                    sh 'mvn clean compile'
            }
        }


    stage('SONARQUBE') {
                          steps {
                              sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=maman'
                          }
                      }
         stage('JUnit Tests') {
                steps {
                    sh 'mvn test'
                }
            }

    stage('JaCoCo Coverage Report') {
             steps {
                  sh 'mvn jacoco:report'
                    }
                }


    stage('artifact construction') {
                    steps {
                          sh 'mvn package'
                            }
                        }


    stage('Publish Nexus') {
                      steps {
                        sh 'mvn deploy -DskipTests -DaltDeploymentRepository=deploymentRepo::default::http://192.168.33.10:8081/repository/maven-releases/'
                            }
                        }

    stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t my-app:v1.0 .'
                }
            }
        }
    stage('Deploy Docker hub') {
                    steps {
                        script {
                             def dockerImageExists = sh(script: 'docker images -q firasyazid12/devops_project_firas:test', returnStatus: true) == 0

                            if (!dockerImageExists) {
                                sh 'docker build -t firasyazid12/devops_project_firas:test -f  Dockerfile .'
                            }

                             withCredentials([usernamePassword(credentialsId: 'dockerhubpwd', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                                sh "echo \$DOCKER_PASSWORD | docker login -u firasyazid12 --password-stdin"
                                sh 'docker push firasyazid12/devops_project_firas:test'
                            }
                        }
                    }
                }





    stage('Verify Docker Compose Installation') {
                 steps {
                                sh 'docker compose version'
                                }
                            }
                            stage('Docker Compose') {
                                steps {
                                    sh 'docker compose -p project_firas up -d'

                                }
                            }
                            stage('check Docker Compose') {
                                steps {
                                    sh 'docker compose ps'
                                }
                            }




    }

      post {
              always {
                  script {
                       sh 'docker compose down'
                  }
              }
          }


}
