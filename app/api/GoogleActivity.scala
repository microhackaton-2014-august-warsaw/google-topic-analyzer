package api

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Format}


case class GoogleActivity(id: String, content: String)

object GoogleActivity {

  implicit val format: Format[GoogleActivity] =
    ((JsPath \ "id").format[String] and
      (JsPath \ "content").format[String])(GoogleActivity.apply, unlift(GoogleActivity.unapply))

}
