package api

import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Format}


case class GoogleActivities(activityId : String, posts : List[String])

object GoogleActivities {

  implicit val format: Format[GoogleActivities] =
    ((JsPath \ "id").format[String] and
     (JsPath \ "object" \\ "originalContent").format[List[String]]) (GoogleActivities.apply, unlift(GoogleActivities.unapply))

}
