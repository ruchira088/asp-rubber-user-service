package com.ruchij.web.responses

import spray.json.{JsArray, JsObject, JsString, RootJsonWriter}

case class ErrorResponse(errors: List[Exception])

object ErrorResponse
{
  implicit def jsonWriter: RootJsonWriter[ErrorResponse] =
    (errorResponse: ErrorResponse) =>
      JsObject(
        "errors" -> JsArray {
          errorResponse.errors.map(exception => JsString(exception.getMessage)).toVector
        }
      )
}
