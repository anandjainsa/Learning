@Library('prokarmalibrary1')_
pipeline {

    agent {

        label "DockerSlaveAP52"

    }

   /*  tools {

        // Note: this should match with the tool name configured in your jenkins instance (JENKINS_URL/configureTools/)

      //  maven "maven"
      //  jdk "jdk1.8.0_131"

    }
*/
environment {
        //  Define all variables
        
	SERVICEPORT = "8081"
        APPNAME = "phonebookservices"
        QA_NAMESPACE = "ns-middleware-dv1"
        DEV_NAMESPACE = "ns-middleware-dv1"
        DEV_ENVIRONMENT = "dev"
        QA_ENVIRONMENT = "qa"
        DEV_ING_SECRET = "w-ttgtpmg-net-secret"
        DEV_ING_HOST = "npworker.ttgtpmg.net"
        QA_ING_SECRET = "w-ttgtpmg-net-secret"
        QA_ING_HOST = "npworker.ttgtpmg.net"
        
}

    stages {
       stage("Build") {
            steps {
              dockerBuild();
            }
        }
/*       stage("Build") {
            steps {
              mavenBuild();
            }
        }

       stage('Unitesting') {
            steps {
                sonarRun()
            }
        }

        stage('Building and Pushing Container Image') {
            steps {
                dockerBuild()
            }
        }

        stage("Deploying Application to Dev") {
          when {
    	  expression {
               return env.BRANCH_NAME == 'Prokarma-devops';
               }
             }
      	  environment {
                 NAMESPACE = "${DEV_NAMESPACE}"
                 ENVIRONMENT = "${DEV_ENVIRONMENT}"
                 SECRET = "${DEV_ING_SECRET}"
                 HOST = "${DEV_ING_HOST}"
        	}
            steps {
	      	 kubeDeployment("${NAMESPACE}", "${APPNAME}", "${SERVICEPORT}", "${ENVIRONMENT}", "${SECRET}", "${HOST}")
		}
        }   
        stage('Approvals') {
            steps {
                approval()

            }
        }

        stage("Deploying Application to QA") {
      	  environment {
                 NAMESPACE = "${QA_NAMESPACE}"
                 ENVIRONMENT = "${QA_ENVIRONMENT}"
                 SECRET = "${QA_ING_SECRET}"
                 HOST = "${QA_ING_HOST}"
		}
            steps {
              kubeDeployment("${NAMESPACE}", "${APPNAME}", "${SERVICEPORT}", "${ENVIRONMENT}", "${SECRET}", "${HOST}")
            }
        }
*/    }
}
