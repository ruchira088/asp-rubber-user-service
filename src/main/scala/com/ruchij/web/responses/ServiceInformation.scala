package com.ruchij.web.responses

import com.eed3si9n.ruchij.BuildInfo
import spray.json.RootJsonFormat

import scala.util.Properties

case class ServiceInformation(
     name: String,
     version: String,
     scalaVersion: String,
     javaVersion: String,
     sbtVersion: String
)

object ServiceInformation
{
  import spray.json.DefaultJsonProtocol._

  def apply(): ServiceInformation =
    ServiceInformation(
      BuildInfo.name,
      BuildInfo.version,
      BuildInfo.scalaVersion,
      Properties.javaVersion,
      BuildInfo.sbtVersion
    )

  implicit def jsonFormat: RootJsonFormat[ServiceInformation] = jsonFormat5(ServiceInformation.apply)
}
