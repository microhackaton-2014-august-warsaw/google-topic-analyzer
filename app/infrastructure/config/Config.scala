package infrastructure.config

import play.api.Configuration
import scala.collection.JavaConverters._
import javax.inject.{Inject, Singleton}


/**
 *
 *
 * @author Lukasz Olczak
 */
@Singleton
class Config @Inject()(configuration: Configuration) {

  lazy val hostname: String = System.getProperty("http.address", "localhost")

  lazy val port: Int = System.getProperty("http.port", "9000").toInt

  lazy val uriSpec = configuration.getString("services.uriSpec").getOrElse("{scheme}://{address}:{port}/")

  lazy val servicesToExport = configuration.getConfigList("services.export")
    .map(_.asScala.toList).getOrElse(List.empty)
    .map(config => ServiceToExport(config.getString("context").get, config.getString("name").get))


}
