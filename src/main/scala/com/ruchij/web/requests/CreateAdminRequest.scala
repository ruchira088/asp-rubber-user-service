package com.ruchij.web.requests

import spray.json.RootJsonFormat

case class CreateAdminRequest(
    username: String,
    password: String,
    email: String,
    firstName: String,
    lastName: Option[String]
)

object CreateAdminRequest
{
  import spray.json.DefaultJsonProtocol._

  implicit def rootJsonFormat: RootJsonFormat[CreateAdminRequest] =
    jsonFormat5(CreateAdminRequest.apply)
}