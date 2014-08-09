package api

import play.api.libs.json.{JsPath, Format}
import play.api.libs.functional.syntax._

/**
 *
 *
 * @author Lukasz Olczak
 */
case class FailResult(reason: String)

object FailResult {

  implicit val format: Format[FailResult] = (JsPath \ "failure").format[String].inmap(FailResult.apply, unlift(FailResult.unapply))

}
