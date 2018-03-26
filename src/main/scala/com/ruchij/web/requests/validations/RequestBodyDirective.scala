package com.ruchij.web.requests.validations

import akka.http.scaladsl.server.Directives.{entity, reject}
import akka.http.scaladsl.server.{Directive, Directive1}
import akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller

object RequestBodyDirective
{
  case class BodyParser[T](
      requestUnmarshaller: FromRequestUnmarshaller[T],
      itemValidator: ItemValidator[T]
  )

  def requestBody[T](bodyParser: BodyParser[T]): Directive1[T] =
    Directive[Tuple1[T]] {
      inner => entity(bodyParser.requestUnmarshaller) {
        item =>
          bodyParser.itemValidator.validate(item) match {
            case Nil => inner(Tuple1(item))
            case validationErrors => reject(ItemValidationRejection(validationErrors))
          }
      }
    }

  def getAs[T](
    implicit requestUnmarshaller: FromRequestUnmarshaller[T],
    itemValidator: ItemValidator[T]
   ): BodyParser[T] = BodyParser(requestUnmarshaller, itemValidator)
}
