package extractor

import org.specs2.mutable._


class TopicExtractorTest extends Specification {

  "Topic Extractor" should {
    val topicExtractor = new TopicExtractor()

    "Return empty list for content without hashtags" in {
      topicExtractor.analyze("Hashless content blabla bla.") must beEmpty
    }

    "Return one topic for content with one hashtag" in {
      topicExtractor.analyze("WTF #Scala content") === List("scala")
    }

    "Return multiple topics fro content with many multiple hashtags" in {
      topicExtractor.analyze("WTF #Scala content #topic") === List("scala", "topic")
    }
  }
}
