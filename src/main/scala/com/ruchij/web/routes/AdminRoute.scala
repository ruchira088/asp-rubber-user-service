package com.ruchij.web.routes

import akka.http.scaladsl.server.Directives._

object AdminRoute
{
  def apply() =
    path("admin") {
      post {

      }
    }
}
