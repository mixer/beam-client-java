node {
    try {
        env.PATH = "${tool 'Maven 3'}/bin:${env.PATH}"

        stage("Checkout") {
            checkout scm
        }
        stage("Maven clean") {
            sh 'mvn clean'
        }
        stage("Maven compile") {
        	sh 'mvn compile'
        }
        stage("Maven test install") {
        	sh 'mvn test install'
        }
        if (env.GIT_BRANCH == "master") {
	        stage("Maven deploy") {
	            sh 'mvn deploy -DaltDeploymentRepository=beam-repo::default::https://maven.beam.pro/content/repositories/snapshots/'
	        }
	    }
        currentBuild.result = "SUCCESS"
    } catch(e) {
        currentBuild.result = "FAILURE"
        throw e
    }
}
