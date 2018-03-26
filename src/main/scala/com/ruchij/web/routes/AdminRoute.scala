package com.ruchij.web.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ruchij.web.requests.CreateAdminRequest
import com.ruchij.web.requests.validations.RequestBodyDirective._

object AdminRoute
{
  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

  def apply(): Route =
    path("admin") {
      post {
        requestBody(getAs[CreateAdminRequest]) {
          createAdminRequest => complete(createAdminRequest)
        }
      }
    }
}
