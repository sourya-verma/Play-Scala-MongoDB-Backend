package models

case class University( _id:Int, name : String, location: String)

case class UniversityJoinStudentData( _id:Int, name : String, location: String,studentCnt:Int)

case class UniversityJoinStudent( _id:Int, name : String, location: String, studentArr:Array[Student])