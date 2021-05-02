package repositories

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}
import models.{Student, StudentJoinUniversity}
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID, document}
import org.joda.time.DateTime
import reactivemongo.api.bson.compat.toDocumentWriter
import reactivemongo.api.commands.WriteResult
import utils.JsonFomrat._
//import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class StudentRepository @Inject()(
                                 implicit executionContext: ExecutionContext,
                                 reactiveMongoApi: ReactiveMongoApi
                               ) {
  def collectionStudent: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("student"))
  def collectionUniversity: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("university"))

  def getAll(limit: Int = -1): Future[Seq[Student]] = {

    collectionStudent.flatMap(
      _.find(BSONDocument(), Option.empty[Student])
        .cursor[Student]()
        .collect[Seq](limit, Cursor.FailOnError[Seq[Student]]())
    )
  }

  def getById(id: Int): Future[Option[Student]] = {
    collectionStudent.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[Student]).one[Student])
  }


  def create(student: Student): Future[Unit] = {
    collectionStudent.flatMap(_.insert.one(student).map(_ => {}))
  }


  def update(student: Student): Future[Int] = {
    val selector = document(
      "_id" -> student._id
    )
    collectionStudent.flatMap(_.update.one(selector, student).map(_.n))
  }

  def delete(id: Int):Future[WriteResult] = {
    collectionStudent.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }

//  def searchStudentByName(name:String):Future[String] = {
//    collection.flatMap(_.find(BSONDocument("name" -> name), Option.empty[Student]).one[Student])
//  }


  def searchStudentByName(name:String): Future[Seq[Student]] = {

    collectionStudent.flatMap(
      _.find(BSONDocument("name" -> name), Option.empty[Student])
        .cursor[Student]()
        .collect[Seq](-1, Cursor.FailOnError[Seq[Student]]())
    )
  }


  def studentWithUniversityName() = {
    def find(university:BSONCollection, student:BSONCollection)= {
      import student.aggregationFramework.Lookup
      student.aggregatorContext[StudentJoinUniversity](
        Lookup(university.name,"universityId","_id","universityArr")
      ).prepared.cursor.collect[List](-1,Cursor.FailOnError[List[StudentJoinUniversity]]())
    }
    collectionUniversity.flatMap(x=>collectionStudent.flatMap(y => find(x,y)))
  }


}

