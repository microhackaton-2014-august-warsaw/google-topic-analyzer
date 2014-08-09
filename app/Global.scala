import com.google.inject.{AbstractModule, Guice}
import com.wordnik.swagger.config.ConfigFactory
import com.wordnik.swagger.model.ApiInfo
import delegate.{ZookeeperTimeServiceDelegate, TimeServiceDelegate}
import infrastructure.ServiceExporter
import org.apache.curator.framework.{CuratorFrameworkFactory, CuratorFramework}
import org.apache.curator.retry.RetryNTimes
import play.api.{Application, GlobalSettings}

/**
 *
 *
 * @author Lukasz Olczak
 */
object Global extends GlobalSettings {

  val zookeeperUrl = configuration.getString("zookeeper.url").getOrElse("127.0.0.1:2181")

  lazy val curatorFramework: CuratorFramework = CuratorFrameworkFactory.newClient(zookeeperUrl, new RetryNTimes(20, 5000))

  val injector = Guice.createInjector(new AbstractModule {
    protected def configure() {
      bind(classOf[CuratorFramework]).toInstance(curatorFramework)
      bind(classOf[TimeServiceDelegate]).to(classOf[ZookeeperTimeServiceDelegate])
    }
  })

  val info = ApiInfo(
    title = "Play Scala Microservice API",
    description = """APIs for Play Scala Microservice template""",
    termsOfServiceUrl = "http://helloreverb.com/terms/",
    contact = "My Apps API Contact Email",
    license = "Apache 2.0",
    licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html")

  ConfigFactory.config.setApiInfo(info)

  var serviceExporter: ServiceExporter = _

  override def onStart(app: Application): Unit = {
    curatorFramework.start()
    serviceExporter = new ServiceExporter(curatorFramework, app.configuration)
  }

  override def onStop(app: Application): Unit = {
    serviceExporter.stop()
    curatorFramework.close()
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)

}
