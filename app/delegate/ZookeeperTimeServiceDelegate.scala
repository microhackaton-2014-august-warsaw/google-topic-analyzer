package delegate

import scala.util.Try
import org.joda.time.DateTime
import javax.inject.Inject
import org.apache.curator.framework.CuratorFramework
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder
import play.api.libs.ws.WS
import api.TimeResult
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global
import play.api.Logger
import scala.concurrent._
import scala.concurrent.duration._

/**
 *
 *
 * @author Lukasz Olczak
 */
class ZookeeperTimeServiceDelegate @Inject()(curatorFramework: CuratorFramework) extends TimeServiceDelegate {

  val serviceDiscovery = ServiceDiscoveryBuilder.builder(classOf[Void]).basePath("pl").client(curatorFramework).build()

  serviceDiscovery.start()

  val serviceProvider = serviceDiscovery.serviceProviderBuilder().serviceName("pl/microhackaton/play_scala/time/now").build()

  serviceProvider.start()

  def now(): Try[DateTime] = Try {
    val serviceInstance = serviceProvider.getInstance()
    val url = serviceInstance.buildUriSpec()
    Logger.debug(s"Found $url")
    val result = WS.url(url).get().map {
      response =>
        response.json.as[TimeResult].time
    }

    Await.result(result, 10 seconds)
  }


}
