import Dependencies._

lazy val root = (project in file("."))
  .settings(
    inThisBuild(List(
      organization := "com.ruchij",
      scalaVersion := "2.12.6"
    )),

    name := "asp-rubber-user-service",
    buildInfoKeys := BuildInfoKey.ofN(name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.eed3si9n.ruchij",

    assemblyJarName in assembly := "asp-rubber-user-service.jar",

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

testOptions in Test +=
  Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/test-results")

addCommandAlias("testWithCoverage", "; clean; coverage; test; coverageReport")