def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    def version = "${env.BUILD_NUMBER}"
    def gradleVersion = '-Pversion=' + version
    def server = Artifactory.server 'artifactory'
    def rtGradle = Artifactory.newGradleBuild()
    rtGradle.useWrapper = true
    rtGradle.deployer server: server, repo: 'maven'
    rtGradle.run switches: gradleVersion + ' --info', tasks: 'sonarqube'

}
