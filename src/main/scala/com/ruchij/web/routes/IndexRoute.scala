package com.ruchij.web.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.{ToResponseMarshallable, ToResponseMarshaller}
import akka.http.scaladsl.model.StatusCodes.UnprocessableEntity
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{RejectionHandler, Route}
import com.ruchij.services.AdminService
import com.ruchij.web.requests.validations.ItemValidationRejection
import com.ruchij.web.responses.{ErrorResponse, ServiceInformation}

import scala.util
import scala.util.{Success, Try}

object IndexRoute
{
  def apply(adminService: AdminService) =
    handleRejections(rejectionHandler) {
      AdminRoute(adminService) ~
      path("info") {
        get {
          complete(ServiceInformation())
        }
      }
    }


  def rejectionHandler: RejectionHandler =
    RejectionHandler.newBuilder()
        .handle {
          case ItemValidationRejection(validationErrors) =>
            complete(UnprocessableEntity, ErrorResponse(validationErrors))
        }
        .result()

  def responseHandler[A, B](transform: A => B)
         (implicit toResponseMarshaller: ToResponseMarshaller[B]): PartialFunction[Try[A], Route] =
  {
    case Success(result) => complete(transform(result))
  }

  def responseHandler[A](implicit toResponseMarshaller: ToResponseMarshaller[A]): PartialFunction[Try[A], Route] =
    responseHandler[A, A](identity)
}
