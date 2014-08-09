package extractor

import org.specs2.mutable._


class TopicExtractorTest extends Specification {

  "Topic Extractor" should {
    "Return empty list for content without hashtags" in {
      val topicExtractor = new TopicExtractor()
      topicExtractor.extractTopicsFrom("Hashless content blabla bla.") must beEmpty
    }
  }
}
