package extractor

import analyzer.ActivityAnalyser

class TopicExtractor extends ActivityAnalyser {

  val hashRegex = """(?:\s|\A)[##]+([A-Za-z0-9-_]+)""".r

  def analyze(activityContent: String): List[String] = {
    hashRegex.findAllIn(activityContent).matchData.map(_.group(1)).map(_.toLowerCase()).toList
  }
}
