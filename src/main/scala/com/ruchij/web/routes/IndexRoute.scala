package com.ruchij.web.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.RejectionHandler
import com.ruchij.web.requests.validations.ItemValidationRejection
import com.ruchij.web.responses.{ErrorResponse, ServiceInformation}

object IndexRoute
{
  def apply() =
    handleRejections(rejectionHandler) {
      AdminRoute() ~
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
}
