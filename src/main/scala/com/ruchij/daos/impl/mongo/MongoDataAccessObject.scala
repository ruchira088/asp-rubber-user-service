package com.ruchij.daos.impl.mongo

import com.eed3si9n.ruchij.BuildInfo
import com.ruchij.constants.ConfigValues
import com.ruchij.daos.DataAccessObject
import com.ruchij.exceptions.MongoInsertionException
import com.ruchij.utils.GeneralUtils.normalize
import com.ruchij.utils.ScalaUtils.predicate
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.{Cursor, MongoConnection, MongoDriver}
import reactivemongo.bson.{BSONDocument, BSONDocumentHandler, BSONValue, BSONWriter}

import scala.concurrent.{ExecutionContext, Future}

class MongoDataAccessObject[A](bsonCollection: BSONCollection)
          (implicit documentHandler: BSONDocumentHandler[A], executionContext: ExecutionContext)
  extends DataAccessObject[A]
{
  override type Selector = BSONDocument

  override def insert(item: A): Future[A] =
    for {
      writeResult <- bsonCollection.insert(item)

      _ <- predicate(writeResult.ok, MongoInsertionException(writeResult.writeErrors.toList))
    }
      yield item

  override def find(selector: BSONDocument): Future[List[A]] =
    bsonCollection.find(selector).cursor[A]()
      .collect[List](ConfigValues.MONGO_MAX_QUERY_RESULT_SIZE, Cursor.FailOnError[List[A]]())

  def findByKeyValue[T](tuple2: (String, T))(implicit bsonWriter: BSONWriter[T, _ <: BSONValue]): Future[List[A]] =
    tuple2 match {
      case (key, value) => find(BSONDocument(key -> value))
    }
}

object MongoDataAccessObject
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
