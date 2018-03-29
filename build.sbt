import Dependencies._

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.ruchij",
      scalaVersion := "2.12.5"
    )),

    name := "asp-user-service",
    buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.eed3si9n.ruchij",

    libraryDependencies ++= Seq(
      akkaActor, akkaStream, akkaHttp, akkaHttpSprayJson,
      jodaTime,
      jbcrypt,
      scalaz,
      reactiveMongo,
      scalaLogging, logbackClassic,

      scalaTest % Test,
      pegdown % Test
    )
  )

enablePlugins(BuildInfoPlugin)

coverageEnabled := true

testOptions in Test +=
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-results")

addCommandAlias("testWithCoverage", "; clean; test; coverageReport")