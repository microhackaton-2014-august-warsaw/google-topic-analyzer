package infrastructure.guice

import com.google.inject.spi.{InjectionListener, TypeEncounter, TypeListener}
import com.google.inject.TypeLiteral
import play.api.Logger
import scala.collection.mutable.ListBuffer

/**
 *
 *
 * @author Lukasz Olczak
 */
class ListenerCollector[A] extends TypeListener {

  private var listeners: ListBuffer[A] = new ListBuffer[A]

  def hear[I](typeLiteral: TypeLiteral[I], encounter: TypeEncounter[I]) {
    Logger.debug(s"Found $typeLiteral")
    encounter.register(new InjectionListener[I] {
      def afterInjection(injectee: I) {
        listeners += injectee.asInstanceOf[A]
      }
    })
  }

  def getListeners: List[A] = listeners.toList

}
