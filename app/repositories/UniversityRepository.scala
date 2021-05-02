package repositories

import javax.inject._
import reactivemongo.api.bson.collection.BSONCollection
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.{ExecutionContext, Future}
import models.{University, UniversityJoinStudent}
import reactivemongo.api.Cursor
import reactivemongo.bson.{BSONDocument, document}
import reactivemongo.api.bson.compat.toDocumentWriter
import reactivemongo.api.commands.WriteResult
import utils.JsonFomrat._

@Singleton
class UniversityRepository @Inject()(
                                   implicit executionContext: ExecutionContext,
                                   reactiveMongoApi: ReactiveMongoApi
                                 ) {
  def collectionUniversity: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("university"))
  def collectionStudent: Future[BSONCollection] = reactiveMongoApi.database.map(db => db.collection("student"))

  def getAll(limit: Int = -1): Future[Seq[University]] = {

    collectionUniversity.flatMap(
      _.find(BSONDocument(), Option.empty[University])
        .cursor[University]()
        .collect[Seq](limit, Cursor.FailOnError[Seq[University]]())
    )
  }

  def getById(id: Int): Future[Option[University]] = {
    collectionUniversity.flatMap(_.find(BSONDocument("_id" -> id), Option.empty[University]).one[University])
  }


  def create(university: University): Future[Unit] = {
    collectionUniversity.flatMap(_.insert.one(university).map(_ => {}))
  }


  def update(university: University): Future[Int] = {
    val selector = document(
      "_id" -> university._id
    )
    collectionUniversity.flatMap(_.update.one(selector, university).map(_.n))
  }

  def delete(id: Int):Future[WriteResult] = {
    collectionUniversity.flatMap(
      _.delete().one(BSONDocument("_id" -> id), Some(1))
    )
  }

  def searchUniversityByName(name:String): Future[Seq[University]] = {

    collectionUniversity.flatMap(
      _.find(BSONDocument("name" -> name), Option.empty[University])
        .cursor[University]()
        .collect[Seq](-1, Cursor.FailOnError[Seq[University]]())
    )
  }


 def universityWithStudentCount()= {
   def find(student:BSONCollection, university: BSONCollection)={
      import university.aggregationFramework.Lookup
     university.aggregatorContext[UniversityJoinStudent](
       Lookup(student.name,"_id","universityId","studentArr")
     ).prepared.cursor.collect[List](-1,Cursor.FailOnError[List[UniversityJoinStudent]]())
   }
   collectionStudent.flatMap(x=>collectionUniversity.flatMap(y => find(x,y)))
 }



  // Repository methods ...
}

