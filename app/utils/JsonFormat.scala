package utils
import models.{Student, StudentJoinUniversity, StudentJoinUniversityData, University, UniversityJoinStudent, UniversityJoinStudentData}
import play.api.libs.json.{Format, Json}
import reactivemongo.bson._
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}


object JsonFomrat{

  implicit val fmtStudent : Format[Student] = Json.format[Student]
  implicit val fmtStudentJoin : Format[StudentJoinUniversity] = Json.format[StudentJoinUniversity]
  implicit val fmtUniversityJoin : Format[UniversityJoinStudent] = Json.format[UniversityJoinStudent]
  implicit val fmtUniversityJData : Format[UniversityJoinStudentData] = Json.format[UniversityJoinStudentData]
  implicit val fmtStudentJData : Format[StudentJoinUniversityData] = Json.format[StudentJoinUniversityData]


  implicit def studentReader: BSONDocumentReader[Student] = Macros.reader[Student]
  implicit def studentWriter: BSONDocumentWriter[Student] = Macros.writer[Student]

  implicit def universityReader: BSONDocumentReader[University] = Macros.reader[University]
  implicit def universityWriter: BSONDocumentWriter[University] = Macros.writer[University]


  implicit def universityJoinReader: BSONDocumentReader[UniversityJoinStudent] = Macros.reader[UniversityJoinStudent]
  implicit def universityJoinWriter: BSONDocumentWriter[UniversityJoinStudent] = Macros.writer[UniversityJoinStudent]


  implicit def universityDataReader: BSONDocumentReader[UniversityJoinStudentData] = Macros.reader[UniversityJoinStudentData]
  implicit def universityDataWriter: BSONDocumentWriter[UniversityJoinStudentData] = Macros.writer[UniversityJoinStudentData]

  implicit def studentJoinReader: BSONDocumentReader[StudentJoinUniversity] = Macros.reader[StudentJoinUniversity]
  implicit def studentJoinWriter: BSONDocumentWriter[StudentJoinUniversity] = Macros.writer[StudentJoinUniversity]


  implicit def studentDataReader: BSONDocumentReader[StudentJoinUniversityData] = Macros.reader[StudentJoinUniversityData]
  implicit def studentDataWriter: BSONDocumentWriter[StudentJoinUniversityData] = Macros.writer[StudentJoinUniversityData]



  //  implicit object StudentBSONReader extends BSONDocumentReader[Student] {
//    def read(doc: BSONDocument): Student = {
//      Student(
//        doc.getAs[Int]("_id").get,
//        doc.getAs[String]("name").get,
//        doc.getAs[String]("email").get,
//        doc.getAs[Int]("universityId").get)
//
//    }
//  }
//  implicit object StudentBSONWriter extends BSONDocumentWriter[Student] {
//    def write(student: Student): BSONDocument = {
//      BSONDocument(
//        "_id" -> student._id,
//        "name" -> student.name,
//        "email" -> student.email,
//        "universityId" -> student.universityId
//      )
//    }
//  }

//-------------------------------------------------------------
//implicit object StudentJoinBSONReader extends BSONDocumentReader[StudentJoin] {
//  def read(doc: BSONDocument): StudentJoin = {
//    StudentJoin(
//      doc.getAs[Int]("_id").get,
//      doc.getAs[String]("name").get,
//      doc.getAs[String]("email").get,
//      doc.getAs[Int]("universityId").get,
//      doc.getAs[Array[University]]("universityArr").get
//    )
//
//  }
//}
//  implicit object StudentJoinBSONWriter extends BSONDocumentWriter[StudentJoin] {
//    def write(student: StudentJoin): BSONDocument = {
//      BSONDocument(
//        "_id" -> student._id,
//        "name" -> student.name,
//        "email" -> student.email,
//        "universityId" -> student.universityId,
//        "universityArr" -> student.universityArr
//      )
//    }
//  }



  //------------------------------------------------------


  //-------------------------------------------------------------
//  implicit object UniversityJoinBSONReader extends BSONDocumentReader[UniversityJoin] {
//    def read(doc: BSONDocument): UniversityJoin = {
//      UniversityJoin(
//        doc.getAs[Int]("_id").get,
//        doc.getAs[String]("name").get,
//        doc.getAs[String]("location").get,
//        doc.getAs[Array[Student]]("studentArr").getOrElse(0)
//      )
//
//    }
//  }
//  implicit object UniversityJoinBSONWriter extends BSONDocumentWriter[UniversityJoin] {
//    def write(student: UniversityJoin): BSONDocument = {
//      BSONDocument(
//        "_id" -> student._id,
//        "name" -> student.name,
//        "location" -> student.location,
//        "studentArr" -> student.studentArr
//      )
//    }
//  }



  //------------------------------------------------------






implicit val fmtUniversity : Format[University] = Json.format[University]
//
//
//  implicit object UniversityBSONReader extends BSONDocumentReader[University] {
//    def read(doc: BSONDocument): University = {
//      University(
//        doc.getAs[Int]("_id").get,
//        doc.getAs[String]("name").get,
//        doc.getAs[String]("location").get)
//
//    }
//  }
//
//  implicit object UniversityBSONWriter extends BSONDocumentWriter[University] {
//    def write(university: University): BSONDocument = {
//      BSONDocument(
//        "_id" -> university._id,
//        "name" -> university.name,
//        "location" -> university.location,
//      )
//    }
//  }
}