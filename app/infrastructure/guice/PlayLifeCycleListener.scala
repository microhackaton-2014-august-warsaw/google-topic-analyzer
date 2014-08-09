package infrastructure.guice

/**
 *
 *
 * @author Lukasz Olczak
 */
trait PlayLifeCycleListener {

  def onStart() = {}

  def onStop() = {}

}
