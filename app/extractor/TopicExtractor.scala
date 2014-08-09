package extractor

class TopicExtractor {

  val hashRegex = """(?:\s|\A)[##]+([A-Za-z0-9-_]+)""".r

  def extractTopicsFrom(activityContent: String): List[String] = {
    hashRegex.findAllIn(activityContent).matchData.map(_.group(1)).map(_.toLowerCase()).toList
  }
}
