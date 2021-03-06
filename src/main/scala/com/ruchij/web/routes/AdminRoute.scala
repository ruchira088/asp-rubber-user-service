package com.ruchij.web.routes

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.ruchij.models.Administrator
import com.ruchij.services.AdminService
import com.ruchij.web.requests.CreateAdminRequest
import com.ruchij.web.requests.validations.RequestBodyDirective._
import com.ruchij.web.utils.JsonFormats._
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonWriter

object AdminRoute
{
  import IndexRoute.responseHandler

  def apply(adminService: AdminService): Route =
    path("admin") {
      post {
        requestBody(getAs[CreateAdminRequest]) {
          createAdminRequest =>
            onComplete(adminService.create(createAdminRequest))(responseHandler)
        }
      }
    }

  implicit def adminWriter: RootJsonWriter[Administrator] =
    (admin: Administrator) => jsonFormat7(Administrator.apply).write(admin.sanitize)
}
