def label = "jenkins-slave-${UUID.randomUUID().toString()}"
podTemplate(
    label: label,
    containers: [
        containerTemplate(name: 'kubectl', image: 'lachlanevenson/k8s-kubectl', ttyEnabled: true, command: 'cat'),
        containerTemplate(name: 'maven', image: 'maven:3.3.9-jdk-8-alpine', ttyEnabled: true, command: 'cat'),
        containerTemplate(name: 'docker', image: 'docker', ttyEnabled: true, command: 'cat')
    ],
    volumes: [ 
        secretVolume(secretName: 'kube-config', mountPath: '/root/.kube'),
        hostPathVolume(mountPath: '/var/run/docker.sock', hostPath: '/var/run/docker.sock'),
        persistentVolumeClaim(claimName: 'nfs-pvc', mountPath: '/root/.m2')
    ]
)
{
    node(label) {
      
        def DOCKER_IMAGE_NAME = 'docker.pkg.github.com/misoboy/k8s-demo-app/k8s-demo-app'
        def DOCKER_IMAGE_FULL_NAME = "${DOCKER_IMAGE_NAME}:${env.BUILD_NUMBER}"
        def POD_NAMESPACE = 'default'
        def K8S_DEPLOYMENT_NAME = 'k8s-demo-app'
        
        stage('init'){
            sh '''
                git config --global http.sslVerify false
            '''
        }
        
        stage('clone'){
            git (
                branch: "k8s-app",
                credentialsId: "${gitCredentials}",
                url: "https://github.com/misoboy/k8s-demo-app.git"
            )
        }
        
        stage('maven'){
            container('maven'){
                stage('maven-build'){
                  sh '''
                    mvn clean install -DskipTests=true
                  '''
                }
            }
        }
        
        stage('docker'){
            container('docker'){
                def DOCKER_IMAGE_OBJ
        
                stage('docker-build') {
                    DOCKER_IMAGE_OBJ = docker.build("${DOCKER_IMAGE_FULL_NAME}",  "--rm=true -f Dockerfile ${WORKSPACE}")
                }
        
                stage('docker-push'){
                    withDockerRegistry(credentialsId: "${dockerCredentials}", url: "https://${DOCKER_IMAGE_FULL_NAME}") {
                        DOCKER_IMAGE_OBJ.push()
                    }
                }
            }
        }
        
        stage('kubernetes'){
            container('kubectl') { 
                stage('kubernetes-deploy') { 
                    sh """
                        kubectl set image -n ${POD_NAMESPACE} deployment/${K8S_DEPLOYMENT_NAME}-deploy ${K8S_DEPLOYMENT_NAME}=${DOCKER_IMAGE_FULL_NAME}
                    """
                } 
            }
        }
    }
}
