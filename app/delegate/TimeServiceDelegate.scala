package delegate

import org.joda.time.DateTime
import scala.util.Try

/**
 *
 *
 * @author Lukasz Olczak
 */
trait TimeServiceDelegate {

  def now() : Try[DateTime]

}
