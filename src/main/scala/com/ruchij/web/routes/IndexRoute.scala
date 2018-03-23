package com.ruchij.web.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import com.ruchij.web.responses.ServiceInformation

object IndexRoute
{
  def apply() =
    path("info") {
      get {
        complete(ServiceInformation())
      }
    }
}
