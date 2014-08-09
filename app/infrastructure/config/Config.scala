package infrastructure.config


/**
 *
 *
 * @author Lukasz Olczak
 */
object Config {

  lazy val hostname: String = System.getProperty("http.address", "localhost")

  lazy val port: Int = System.getProperty("http.port", "9000").toInt

}
