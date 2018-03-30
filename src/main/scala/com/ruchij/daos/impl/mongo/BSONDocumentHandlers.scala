package com.ruchij.daos.impl.mongo

import com.ruchij.models.Administrator
import org.joda.time.DateTime
import reactivemongo.bson.{BSONDocumentHandler, Macros}

object BSONDocumentHandlers
{
  implicit def jodaDateTimeHandler: BSONDocumentHandler[DateTime] =
    BSONDocumentHandler(???, ???)

  implicit def administratorHandler: BSONDocumentHandler[Administrator] = Macros.handler[Administrator]
}
