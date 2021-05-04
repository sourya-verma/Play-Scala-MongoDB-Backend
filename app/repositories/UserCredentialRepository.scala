package repositories


import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}
import models.{LoginDetail, Student, StudentJoinUniversity, University, UserCredential}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID, document}
import org.joda.time.DateTime
import reactivemongo.api.bson.compat.toDocumentWriter
import reactivemongo.api.commands.WriteResult
import utils.JsonFomrat._
//import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class UserCredentialRepository @Inject()(
                                   implicit executionContext: ExecutionContext,
                                   reactiveMongoApi: ReactiveMongoApi
                                 ) {
  def collectionUser: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("user_credential"))

  def getAll(limit: Int = -1): Future[Seq[UserCredential]] = {

    collectionUser.flatMap(
      _.find(BSONDocument(), Option.empty[UserCredential])
        .cursor[UserCredential]()
        .collect[Seq](limit, Cursor.FailOnError[Seq[UserCredential]]())
    )
  }


  def create(user: UserCredential): Future[Unit] = {
    collectionUser.flatMap(_.insert.one(user).map(_ => {}))
  }


  def update(user: UserCredential): Future[Int] = {
    val selector = document(
      "_id" -> user._id
    )
    collectionUser.flatMap(_.update.one(selector, user).map(_.n))
  }

  def delete(_id: Int):Future[WriteResult] = {
    collectionUser.flatMap(
      _.delete().one(BSONDocument("_id" -> _id), Some(1))
    )
  }

  def validate(user: LoginDetail): Future[Option[UserCredential]] = {
    collectionUser.flatMap(_.find(BSONDocument("_id" -> user._id, "password" -> user.password), Option.empty[UserCredential]).one[UserCredential])
  }

  //  def validate(user: LoginDetail) =  db.run{
//    userCredentialTableQuery.filter(usr=> (usr.userID === user.userID && usr.password=== user.password)).result.headOption
//  }
//


}

