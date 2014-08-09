package controllers

import javax.inject.{Singleton, Inject}

import analyzer.ActivityAnalyser
import api.GoogleActivity
import infrastructure.ServiceAction
import api.AnalysedActivity


// TODO : add controller route
@Singleton
class ActivityController @Inject()(activityAnalyser: ActivityAnalyser) extends BaseApiController {

  def receiveActivity(pairId: String) = ServiceAction {
    activities: List[GoogleActivity] =>
      val results = activities map {
        activity =>
          val topics = activityAnalyser.analyze(activity.content)
          AnalysedActivity(activity.id, topics)
      }
      Right(results)
  }

}
