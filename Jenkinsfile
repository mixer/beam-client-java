node {
	properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '30', artifactNumToKeepStr: '2', daysToKeepStr: '30', numToKeepStr: '2']]])
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
        stage("Maven test") {
        	sh 'mvn test'
        }
        stage("Maven install") {
            sh 'mvn install -DskipTests=true'
        }
        if (env.BRANCH_NAME == "master") {
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
