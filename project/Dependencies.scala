import sbt._

object Dependencies
{
  val AKKA_VERSION = "2.5.12"
  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % AKKA_VERSION
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % AKKA_VERSION

  val AKKA_HTTP_VERSION = "10.1.1"
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % AKKA_HTTP_VERSION
  lazy val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % AKKA_HTTP_VERSION

  lazy val jodaTime = "joda-time" % "joda-time" % "2.9.9"

  lazy val jbcrypt = "org.mindrot" % "jbcrypt" % "0.4"

  lazy val scalaz = "org.scalaz" %% "scalaz-core" % "7.2.23"

  lazy val reactiveMongo = "org.reactivemongo" %% "reactivemongo" % "0.13.0"

  lazy val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
  lazy val logbackClassic = "ch.qos.logback" % "logback-classic" % "1.2.3"

  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val pegdown = "org.pegdown" % "pegdown" % "1.6.0"
}