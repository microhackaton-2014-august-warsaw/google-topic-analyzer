package infrastructure

import play.api.{Logger, Configuration}
import scala.collection.JavaConverters._
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.x.discovery.{ServiceDiscoveryBuilder, UriSpec, ServiceInstance}

/**
 *
 *
 * @author Lukasz Olczak
 */
class ServiceExporter(curatorFramework: CuratorFramework, configuration: Configuration) {

  val uriSpec = configuration.getString("services.uriSpec").getOrElse("{scheme}://{address}:{port}/")

  val servicesToExport = configuration.getConfigList("services.export")
    .map(_.asScala.toList).getOrElse(List.empty)
    .map(config => ServiceToExport(config.getString("context").get, config.getString("name").get))

  val discoveries = {
    servicesToExport map {
      service =>
        Logger.debug(s"Exporting service context: ${service.context} name: ${service.name}")
        val serviceInstance = ServiceInstance.builder[Void]().uriSpec(new UriSpec(uriSpec + service.context))
          .address("localhost")
          .port(9000) //todo
          .name(service.name)
          .build()

        val serviceDiscovery = ServiceDiscoveryBuilder.builder(classOf[Void]).basePath("pl").client(curatorFramework).thisInstance(serviceInstance).build()
        serviceDiscovery.registerService(serviceInstance)
        serviceDiscovery
    }
  }

  def stop() = {
    discoveries.foreach(_.close())
    curatorFramework.close()
  }

}

case class ServiceToExport(context: String, name: String)
