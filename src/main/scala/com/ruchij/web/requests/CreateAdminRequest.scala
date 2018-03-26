package com.ruchij.web.requests

import com.ruchij.utils.ScalaUtils._
import com.ruchij.web.requests.validations.ItemValidator
import com.ruchij.web.requests.validations.ItemValidator.nonEmpty
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

  implicit def requestBodyValidator: ItemValidator[CreateAdminRequest] =
    (createAdminRequest: CreateAdminRequest) =>
      reduceOptions(
        nonEmpty(createAdminRequest.username, "username"),
        nonEmpty(createAdminRequest.password, "password"),
        nonEmpty(createAdminRequest.email, "email"),
        nonEmpty(createAdminRequest.firstName, "firstName")
      )

  implicit def rootJsonFormat: RootJsonFormat[CreateAdminRequest] =
    jsonFormat5(CreateAdminRequest.apply)
}