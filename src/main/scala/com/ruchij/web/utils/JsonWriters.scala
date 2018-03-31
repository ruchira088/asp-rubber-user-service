package com.ruchij.web.utils

import com.ruchij.models.Administrator
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonWriter

object JsonWriters
{
  import JsonFormats._

  implicit def adminWriter: RootJsonWriter[Administrator] =
    (admin: Administrator) => jsonFormat7(Administrator.apply).write(admin.sanitize)
}