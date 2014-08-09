package api

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime

/**
 *
 *
 * @author Lukasz Olczak
 */
case class Ping(msg: String)

object Ping {

  implicit val format: Format[Ping] = (JsPath \ "ping").format[String].inmap(Ping.apply, unlift(Ping.unapply))

}

case class Pong(msg: String, timestamp: DateTime)

object Pong {

  implicit val format: Format[Pong] =
    ((JsPath \ "pong").format[String] and
      (JsPath \ "timestamp").format[DateTime]) (Pong.apply, unlift(Pong.unapply))

}
