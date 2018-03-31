package com.ruchij.web.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ruchij.services.AdminService
import com.ruchij.web.requests.CreateAdminRequest
import com.ruchij.web.requests.validations.RequestBodyDirective._
import com.ruchij.web.utils.JsonWriters._

import scala.util.{Failure, Success}

object AdminRoute
{
  def apply(adminService: AdminService): Route =
    path("admin") {
      post {
        requestBody(getAs[CreateAdminRequest]) {
          createAdminRequest =>
            onComplete(adminService.create(createAdminRequest)) {
              case Success(administrator) => complete(administrator)

              case Failure(_) => ???
            }
        }
      }
    }
}
