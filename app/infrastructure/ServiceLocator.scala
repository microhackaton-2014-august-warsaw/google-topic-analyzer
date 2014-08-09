package infrastructure

/**
 *
 *
 * @author Lukasz Olczak
 */
trait ServiceLocator {

  def lookup(name: String): String

}
