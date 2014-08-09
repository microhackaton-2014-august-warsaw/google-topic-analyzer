package controllers

import javax.inject.{Singleton, Inject}

import analyzer.ActivityAnalyser
import api.{FailResult, GoogleActivities}
import infrastructure.ServiceAction
import api.AnalysedActivity

import scala.util.{Success, Failure}


// TODO : add controller route
@Singleton
class ActivityController @Inject() (activityAnalyser : ActivityAnalyser) extends BaseApiController {

    def receiveActivity = ServiceAction {
      activities : GoogleActivities => {
        val topicsResult = activityAnalyser.analyze(activities.posts)
        //TODO : call external service with extractor result
        topicsResult match {
          case Success(topics) => Right(AnalysedActivity(activities.activityId, topics)) // 200 ok
          case Failure(exception) => Left(FailResult(exception.getMessage))
        }
      }
    }
}
