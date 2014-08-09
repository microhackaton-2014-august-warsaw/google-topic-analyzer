package analyzer

import api.{GoogleActivity, AnalysedActivity}
import scala.util.Try

trait ActivityAnalyser {
  def analyze(posts : List[String]) : Try[List[String]]
}
