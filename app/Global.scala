import analyzer.ActivityAnalyser
import com.google.inject.{Injector, AbstractModule, Guice}
import com.wordnik.swagger.config.ConfigFactory
import com.wordnik.swagger.model.ApiInfo
import delegate.{ZookeeperTimeServiceDelegate, TimeServiceDelegate}
import extractor.TopicExtractor
import infrastructure.config.Config
import infrastructure.guice.{PlayLifeCycleListener, ListenerCollector, PlayLifeCycleMatcher}
import infrastructure.ServiceExporter
import org.apache.curator.framework.{CuratorFrameworkFactory, CuratorFramework}
import org.apache.curator.retry.RetryNTimes
import play.api.{Configuration, Application, GlobalSettings, Logger}

/**
 *
 *
 * @author Lukasz Olczak
 */
object Global extends GlobalSettings {

  val zookeeperUrl = configuration.getString("zookeeper.url").getOrElse("127.0.0.1:2181")

  var curatorFramework: CuratorFramework = _

  var listenerCollector: ListenerCollector[PlayLifeCycleListener] = _

  def newCuratorClient: CuratorFramework = {
    CuratorFrameworkFactory.newClient(zookeeperUrl, new RetryNTimes(20, 5000))
  }

  var injector: Injector = _

  def newInjector(listenerCollector: ListenerCollector[PlayLifeCycleListener], curatorFramework: CuratorFramework,
                  configuration: Configuration) =
    Guice.createInjector(new AbstractModule {
      protected def configure() {
        bindListener(PlayLifeCycleMatcher, listenerCollector)
        bind(classOf[Configuration]).toInstance(configuration)
        bind(classOf[Config]).asEagerSingleton()
        bind(classOf[ServiceExporter]).asEagerSingleton()
        bind(classOf[CuratorFramework]).toInstance(curatorFramework)
        bind(classOf[TimeServiceDelegate]).to(classOf[ZookeeperTimeServiceDelegate])
        bind(classOf[ActivityAnalyser]).to(classOf[TopicExtractor])
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


  override def beforeStart(app: Application): Unit = {
    Logger.debug("Global.beforeStart")
    curatorFramework = newCuratorClient
    listenerCollector = new ListenerCollector[PlayLifeCycleListener]
    injector = newInjector(listenerCollector, curatorFramework, app.configuration)
  }

  override def onStart(app: Application): Unit = {
    Logger.debug("Global.onStart")
    curatorFramework.start()
    //    Logger.debug("Curator state: " + curatorFramework.getState)
    //    curatorFramework = newCuratorClient
    //    curatorFramework.start()
    //    serviceExporter = new ServiceExporter(curatorFramework, app.configuration)
    listenerCollector.getListeners foreach (_.onStart())
  }

  override def onStop(app: Application): Unit = {
    Logger.debug("Global onStop")
    listenerCollector.getListeners foreach (_.onStop())
    curatorFramework.close()
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A = injector.getInstance(controllerClass)

}
