package controllers

import infrastructure.ServiceAction
import org.joda.time.{DateTimeZone, DateTime}
import com.wordnik.swagger.annotations._
import javax.inject.Singleton
import api.TimeResult

/**
 *
 *
 * @author Lukasz Olczak
 */
@Api(value = "/time", description = "Time API")
@Singleton
class TimeController extends BaseApiController {

  @ApiOperation(nickname = "now",
    value = "Time Server",
    response = classOf[DateTime],
    httpMethod = "GET")
  def now() = ServiceAction {
    () =>
      Right(TimeResult(DateTime.now(DateTimeZone.UTC)))
  }

}
