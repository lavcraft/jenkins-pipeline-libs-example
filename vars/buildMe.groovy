def call(script, body) {

      def config = [:]
      body.resolveStrategy = Closure.DELEGATE_FIRST
      body.delegate = config
      body()

      println '---------------------------'

      script {
        def server = Artifactory.server 'artifactory'
        def rtGradle = Artifactory.newGradleBuild()

        rtGradle.useWrapper = true
        rtGradle.deployer server: server, repo: 'maven'
      }

      withSonarQubeEnv('sonar') {
        rtGradle.run switches: gradleVersion + ' --info', tasks: 'sonarqube'
      }

      println '-'*100
      println "${script.rtGradle}"

}

