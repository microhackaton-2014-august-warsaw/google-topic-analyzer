package infrastructure

import play.api.Logger
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.x.discovery.{ServiceDiscovery, ServiceDiscoveryBuilder, UriSpec, ServiceInstance}
import infrastructure.config.Config
import javax.inject.Inject
import javax.inject.Singleton
import infrastructure.guice.PlayLifeCycleListener

/**
 *
 *
 * @author Lukasz Olczak
 */
@Singleton
class ServiceExporter @Inject()(curatorFramework: CuratorFramework, config: Config)
  extends PlayLifeCycleListener {

  var discoveries: List[ServiceDiscovery[Void]] = List.empty

  override def onStart(): Unit = {
    Logger.debug(s"Exporting services ${config.servicesToExport}")
    discoveries = config.servicesToExport map {
      service =>
        Logger.debug(s"Exporting service context: ${service.context} name: ${service.name}")
        val serviceInstance = ServiceInstance.builder[Void]().uriSpec(new UriSpec(config.uriSpec + service.context))
          .address(config.hostname)
          .port(config.port)
          .name(service.name)
          .build()

        val serviceDiscovery = ServiceDiscoveryBuilder.builder(classOf[Void])
          .basePath("pl")
          .client(curatorFramework)
          .thisInstance(serviceInstance).build()
        serviceDiscovery.registerService(serviceInstance)
        serviceDiscovery
    }
  }

  override def onStop(): Unit = {
    Logger.debug("Closing service discoveries")
    discoveries.foreach(_.close())
  }

}

