package api

import play.api.libs.json.{JsPath, Format}
import play.api.libs.functional.syntax._

/**
 * Created by grzech on 8/9/14.
 */
case class AnalysedActivity (id : String, topics : List[String])

object AnalysedActivity {

  implicit val format: Format[AnalysedActivity] =
    ((JsPath \ "id").format[String] and
      (JsPath \ "topics").format[List[String]])(AnalysedActivity.apply, unlift(AnalysedActivity.unapply))

}
