def call(body) {

      def config = [:]
      body.resolveStrategy = Closure.DELEGATE_FIRST
      body.delegate = config
      body()

      println '---------------------------'
        

      echo "echo $config.name"

      println '='*100

}
