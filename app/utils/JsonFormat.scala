package utils
import models.{LoginDetail, Student, StudentJoinUniversity, StudentJoinUniversityData, University, UniversityJoinStudent, UniversityJoinStudentData, UserCredential, UserInfo}
import play.api.libs.json.{Format, Json}
//import reactivemongo.bson._
//import play.api.libs.json.Json
import reactivemongo.api.bson.{BSONDocumentReader, BSONDocumentWriter, Macros}


object JsonFomrat{

  implicit val fmtStudent : Format[Student] = Json.format[Student]
  implicit val fmtUniversity : Format[University] = Json.format[University]
  implicit val fmtStudentJoin : Format[StudentJoinUniversity] = Json.format[StudentJoinUniversity]
  implicit val fmtUniversityJoin : Format[UniversityJoinStudent] = Json.format[UniversityJoinStudent]
  implicit val fmtUniversityJData : Format[UniversityJoinStudentData] = Json.format[UniversityJoinStudentData]
  implicit val fmtStudentJData : Format[StudentJoinUniversityData] = Json.format[StudentJoinUniversityData]

  implicit val userLogin = Json.format[LoginDetail]
  implicit val userFormat = Json.format[UserCredential]
  implicit val userInfoFormat = Json.format[UserInfo]


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


  implicit def userCredentialReader: BSONDocumentReader[UserCredential] = Macros.reader[UserCredential]
  implicit def userCredentialWriter: BSONDocumentWriter[UserCredential] = Macros.writer[UserCredential]



  implicit def loginDetailReader: BSONDocumentReader[LoginDetail] = Macros.reader[LoginDetail]
  implicit def loginDetailWriter: BSONDocumentWriter[LoginDetail] = Macros.writer[LoginDetail]


  implicit def userInfoReader: BSONDocumentReader[UserInfo] = Macros.reader[UserInfo]
  implicit def userInfoWriter: BSONDocumentWriter[UserInfo] = Macros.writer[UserInfo]


}