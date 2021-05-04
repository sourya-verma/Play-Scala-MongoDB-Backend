name := """Play-CRUD-MongoDB"""
organization := "com.techsophy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"


//libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.techsophy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.techsophy.binders._"


libraryDependencies ++= Seq(
  // Enable reactive mongo for Play 2.8
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play28",
  // Provide JSON serialization for reactive mongo
  "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.1-play28",
  // Provide BSON serialization for reactive mongo
  "org.reactivemongo" %% "reactivemongo-bson-compat" % "0.20.13",
  // Provide JSON serialization for Joda-Time
  "com.typesafe.play" %% "play-json-joda" % "2.7.4",
  "com.github.jwt-scala" %% "jwt-core" % "7.1.3"
)



//guice,
//"com.typesafe.play" %% "play-slick" % "5.0.0",
//"com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
//"com.h2database" % "h2" % "1.4.187",
//"org.postgresql" % "postgresql" % "9.4-1206-jdbc4",
//"org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
//"org.mockito" %% "mockito-scala" % "1.11.2",
//specs2 % Test,
//"com.github.jwt-scala" %% "jwt-core" % "7.1.3",
//"mysql" % "mysql-connector-java" % "6.0.6"
