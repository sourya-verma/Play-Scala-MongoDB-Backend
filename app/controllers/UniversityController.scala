package controllers

import javax.inject._
import play.api.mvc._
import repositories.UniversityRepository
import utils.JsonFomrat._
import play.api.libs.json.{Json, __}

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import models.{Student, University, UniversityJoinStudentData}
import play.api.libs.json.JsValue
import utils.SecureAction

@Singleton
class UniversityController @Inject()(
                                   implicit executionContext: ExecutionContext,
                                   val universityRepository: UniversityRepository,
                                   secureAction :SecureAction,
                                   val controllerComponents: ControllerComponents)
  extends BaseController {
  // controller actions goes here...

  def list(): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    universityRepository.getAll().map {
      university => Ok(Json.toJson(university)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def getById(id: Int): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    universityRepository.getById(id).map {
      university => Ok(Json.toJson(university)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def create(): Action[JsValue] = secureAction.async(controllerComponents.parsers.json) { implicit request => {
    print(request.body)
    request.body.validate[University].fold(
      _ => Future.successful(BadRequest("Cannot parse request body").withHeaders("Access-Control-Allow-Origin" -> "*")),
      university =>
        universityRepository.create(university).map {
          _ => Created(Json.toJson(university))
        }
    )
  }
  }

  def update: Action[JsValue] = secureAction.async(controllerComponents.parsers.json) { implicit request => {
    print(request.body
    )
    request.body.validate[University].fold(
      _ => Future.successful(BadRequest("Cannot parse request body").withHeaders("Access-Control-Allow-Origin" -> "*")),
      university => {
        universityRepository.update(university).map {
          result => Ok(Json.toJson(result)).withHeaders("Access-Control-Allow-Origin" -> "*")
        }

      }
    )
  }
  }

  def delete(id: Int): Action[AnyContent] = secureAction.async { implicit request => {
    universityRepository.delete(id).map { _ => NoContent }
  }}


  def searchUniversityByName(name: String): Action[AnyContent] = secureAction.async { implicit request: Request[AnyContent] =>
    universityRepository.searchUniversityByName(name).map {
      university => Ok(Json.toJson(university)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def universityWithStudentCount():Action[AnyContent] = secureAction.async{ implicit request : Request[AnyContent] =>{
    universityRepository.universityWithStudentCount().map(res =>{
      val result = for (x <- res) yield UniversityJoinStudentData(x._id, x.name,x.location,x.studentArr.length)
      Ok(Json.toJson(result)).withHeaders("Access-Control-Allow-Origin" -> "*")
    })
  }

  }








}