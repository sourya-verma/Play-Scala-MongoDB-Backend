package models

import reactivemongo.bson.BSONDateTime



case class Student(_id: Int, name : String, email : String, universityId: Int)

case class StudentJoinUniversityData(_id: Int, name : String, email : String, universityName:String)

case class StudentJoinUniversity (_id: Int, name : String, email : String, universityId: Int, universityArr : Array[University])


case class UserCredential(_id:Int,firstName:String, lastName:String, password:String);

case class UserInfo (_id:Int, firstName:String, lastName:String)

case class LoginDetail (_id:Int, password:String)

