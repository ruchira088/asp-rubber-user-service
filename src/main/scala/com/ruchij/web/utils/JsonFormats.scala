package com.ruchij.web.utils

import org.joda.time.DateTime
import spray.json.{JsString, JsValue, RootJsonFormat, deserializationError}

import scala.util.Try

object JsonFormats
{
  implicit def jodaTimeFormat: RootJsonFormat[DateTime] = new RootJsonFormat[DateTime]
  {
    override def read(jsValue: JsValue): DateTime =
      jsValue match {

        case JsString(dateTimeString) =>
          Try(DateTime.parse(dateTimeString))
            .fold(throwable => deserializationError(throwable.getMessage), identity)

        case _ => deserializationError(s"Expected DateTime as JsString, but got $jsValue")
      }

    override def write(dateTime: DateTime): JsValue = JsString(dateTime.toString)
  }
}
