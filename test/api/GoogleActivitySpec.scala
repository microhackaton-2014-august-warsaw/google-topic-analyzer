package api


import org.specs2.mutable._
import play.api.libs.json.Json

object GoogleActivitySpec {

  val SampleJson =
    """
      |{
      |  "id" : "123",
      |  "content" : "test"
      |}
    """.stripMargin

}

class GoogleActivitySpec extends Specification {

  import GoogleActivitySpec._

  "GoogleActivity" should {

    "unmarshal from json" in {
      val result = Json.fromJson[GoogleActivity](Json.parse(SampleJson))
      result.get == GoogleActivity("123", "test")
    }
  }

}
