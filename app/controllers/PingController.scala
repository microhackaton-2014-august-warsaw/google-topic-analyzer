package controllers

import com.wordnik.swagger.annotations._
import scala.Array
import api.{FailResult, Ping, Pong}
import infrastructure.ServiceAction
import delegate.TimeServiceDelegate
import scala.util.{Failure, Success}
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * @author Lukasz Olczak
 */
@Api(value = "/ping", description = "Ping API")
@Singleton
class PingController @Inject() (timeService: TimeServiceDelegate) extends BaseApiController {

  @ApiOperation(nickname = "ping",
    value = "Ping Server",
    response = classOf[Pong],
    httpMethod = "POST")
  @ApiResponses(Array(
    new ApiResponse(code = 405, message = "Invalid input")))
  @ApiImplicitParams(Array(
    new ApiImplicitParam(value = "Ping msg", required = true, dataType = "Ping", paramType = "body")))
  def ping = ServiceAction {
    ping: Ping =>
      val timeResult = timeService.now()
      timeResult match {
        case Success(time) => Right(Pong(ping.msg, time))
        case Failure(ex) => Left(FailResult(ex.getMessage))
      }
  }

}
