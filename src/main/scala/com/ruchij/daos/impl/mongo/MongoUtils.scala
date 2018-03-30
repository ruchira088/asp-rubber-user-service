package com.ruchij.daos.impl.mongo

import com.eed3si9n.ruchij.BuildInfo
import com.ruchij.utils.GeneralUtils.normalize
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{MongoConnection, MongoDriver}

import scala.concurrent.{ExecutionContext, Future}

object MongoUtils
{
  def collection(mongoUri: String, collectionName: String)
                (implicit executionContext: ExecutionContext): Future[BSONCollection] =
    for {
      parsedUri <- Future.fromTry(MongoConnection.parseURI(mongoUri))
      connection = MongoDriver().connection(parsedUri)

      database <- connection.database(normalize(BuildInfo.name))
    }
    yield database.collection[BSONCollection](collectionName)
}
