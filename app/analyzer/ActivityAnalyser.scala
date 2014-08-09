package analyzer

import api.{GoogleActivity, AnalysedActivity}
import scala.util.Try

trait ActivityAnalyser {

  def analyze(post : String) : List[String]

}
