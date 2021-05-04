package controllers

import javax.inject._
import play.api.mvc._
import repositories.{StudentRepository, UniversityRepository}
import utils.JsonFomrat._
import reactivemongo.bson.BSONObjectID
import play.api.libs.json.{Json, __}

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import models.{Student, StudentJoinUniversityData}
import play.api.libs.json.JsValue
import utils.SecureAction

@Singleton
class StudentController @Inject()(
                                 implicit executionContext: ExecutionContext,
                                 val studentRepository: StudentRepository,
                                 val universityRepository: UniversityRepository,
                                 secureAction :SecureAction,
                                 val controllerComponents: ControllerComponents)
  extends BaseController {
  // controller actions goes here...

  def list(): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    studentRepository.getAll().map {
      students => Ok(Json.toJson(students)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def getById(id: Int): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    studentRepository.getById(id).map {
      student => Ok(Json.toJson(student)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def searchStudentByName(name: String): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    studentRepository.searchStudentByName(name).map {
      student => Ok(Json.toJson(student)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def create(): Action[JsValue] = secureAction.async(controllerComponents.parsers.json) { implicit request => {
    print(request.body)
    request.body.validate[Student].fold(
      _ => Future.successful(BadRequest("Cannot parse request body").withHeaders("Access-Control-Allow-Origin" -> "*")),
      student =>
        studentRepository.create(student).map {
          _ => Created(Json.toJson(student))
        }
    )
  }
  }

  def update: Action[JsValue] = secureAction.async(controllerComponents.parsers.json) { implicit request => {
    request.body.validate[Student].fold(
      _ => Future.successful(BadRequest("Cannot parse request body")),
      student => {
        studentRepository.update(student).map {
          result => Ok(Json.toJson(result)).withHeaders("Access-Control-Allow-Origin" -> "*")
        }

      }
    )
  }
  }

  def delete(id: Int): Action[AnyContent] = secureAction.async { implicit request => {
    studentRepository.delete(id).map { _ => NoContent }
  }
  }


  def studentWithUniversityName(): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] => {
    studentRepository.studentWithUniversityName().map(res => {
      val result = for (x <- res) yield StudentJoinUniversityData(x._id, x.name, x.email,x.universityArr.filter(y=> y._id == x.universityId)(0).name)
      Ok(Json.toJson(result)).withHeaders("Access-Control-Allow-Origin" -> "*")
    })
  }
  }












    //  def universityStudentCount : Action[AnyContent] = Action.async{ implicit request:Request[AnyContent]  =>
//    studentRepository.universityStudentCount().map {
//      students => Ok(Json.toJson(students))
//    }
//
//  }
}