package com.ruchij.daos.impl.mongo

import com.ruchij.models.Administrator
import org.joda.time.DateTime
import reactivemongo.bson.{BSONDateTime, BSONDocumentHandler, BSONHandler, Macros}

object BSONDocumentHandlers
{
  implicit def jodaDateTimeHandler: BSONHandler[BSONDateTime, DateTime] =
    BSONHandler(
      bsonDateTime => new DateTime(bsonDateTime.value),
      dateTime => BSONDateTime(dateTime.getMillis)
    )

  implicit def administratorHandler: BSONDocumentHandler[Administrator] = Macros.handler[Administrator]
}
