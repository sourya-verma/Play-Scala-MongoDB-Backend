package controllers



import javax.inject._
import play.api.mvc._
import repositories.{UniversityRepository, UserCredentialRepository}
import utils.JsonFomrat._
import play.api.libs.json.{JsError, JsValue, Json, __}

import scala.util.{Failure, Success}
import scala.concurrent.{ExecutionContext, Future}
import models.{LoginDetail, Student, University, UniversityJoinStudentData, UserCredential, UserInfo}
import utils.AuthService

@Singleton
class UserCredentialController @Inject()(
                                      implicit executionContext: ExecutionContext,
                                      val userCredentialRepository: UserCredentialRepository,
                                      auth : AuthService,
                                      val controllerComponents: ControllerComponents)
  extends BaseController {
  // controller actions goes here...

  def validate: Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body.validate[LoginDetail].fold(error => Future.successful(BadRequest(JsError.toJson(error)).withHeaders("Access-Control-Allow-Origin" -> "*")), { usr =>
        userCredentialRepository.validate(usr:LoginDetail).map {
          case Some(user) =>
            val json = Json.toJson(Map("token"-> auth.encodeToken(UserInfo(user._id, user.firstName, user.lastName))))
            Ok(json).withHeaders("Access-Control-Allow-Origin" -> "*")
          case None =>
            BadRequest("Invalid credential.........")
        }

      })
    }

  def list(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    userCredentialRepository.getAll().map {
      user => Ok(Json.toJson(user)).withHeaders("Access-Control-Allow-Origin" -> "*")
    }
  }

  def create(): Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    print(request.body)
    request.body.validate[UserCredential].fold(
      _ => Future.successful(BadRequest("Cannot parse request body").withHeaders("Access-Control-Allow-Origin" -> "*")),
      user =>
        userCredentialRepository.create(user).map {
          _ => Created(Json.toJson(user)).withHeaders("Access-Control-Allow-Origin" -> "*")
        }
    )
  }
  }

  def update: Action[JsValue] = Action.async(controllerComponents.parsers.json) { implicit request => {
    print(request.body
    )
    request.body.validate[UserCredential].fold(
      _ => Future.successful(BadRequest("Cannot parse request body").withHeaders("Access-Control-Allow-Origin" -> "*")),
      user => {
        userCredentialRepository.update(user).map {
          result => Ok(Json.toJson(result)).withHeaders("Access-Control-Allow-Origin" -> "*")
        }

      }
    )
  }
  }

  def delete(id: Int): Action[AnyContent] = Action.async { implicit request => {
    userCredentialRepository.delete(id).map { _ => NoContent }
  }}
}


