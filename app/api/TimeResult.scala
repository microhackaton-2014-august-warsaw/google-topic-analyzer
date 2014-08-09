package api

import org.joda.time.DateTime
import play.api.libs.json.{JsPath, Format}
import play.api.libs.functional.syntax._

/**
 *
 *
 * @author Lukasz Olczak
 */
case class TimeResult(time: DateTime)

object TimeResult {

  implicit val format: Format[TimeResult] = (JsPath \ "time").format[DateTime].inmap(TimeResult.apply, unlift(TimeResult.unapply))

}
