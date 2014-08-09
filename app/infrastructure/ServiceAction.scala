package infrastructure

import play.api.libs.json.{JsError, Json, Writes, Reads}
import play.api.mvc.BodyParsers.parse
import play.api.mvc.{Results, Action}
import api.FailResult

/**
 *
 *
 * @author Lukasz Olczak
 */
object ServiceAction extends Results {

  def apply[R: Writes](block: () => Either[FailResult, R]) = Action {
    request =>
      val result = block()
      result match {
        case Left(fail) => InternalServerError(Json.toJson(fail))
        case Right(r) => Ok(Json.toJson(r))
      }
  }

  def apply[I: Reads, R: Writes](block: I => Either[FailResult, R]) = Action(parse.json) {
    request =>
      val json = request.body
      json.validate[I].fold(
        valid = {
          input =>
            val result = block(input)
            result match {
              case Left(fail) => InternalServerError(Json.toJson(fail))
              case Right(r) => Ok(Json.toJson(r))
            }
        },
        invalid = {
          errors =>
            BadRequest(JsError.toFlatJson(errors))
              .as("application/json")
        }
      )
  }

}
