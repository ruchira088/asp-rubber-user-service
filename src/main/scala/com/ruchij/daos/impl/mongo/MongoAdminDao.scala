package com.ruchij.daos.impl.mongo

import com.ruchij.daos.AdminDao
import com.ruchij.models.Administrator
import reactivemongo.bson.BSONDocument
import scalaz.OptionT

import scala.concurrent.{ExecutionContext, Future}

class MongoAdminDao(mongoDao: MongoDataAccessObject[Administrator])
                   (implicit val executionContext: ExecutionContext)
  extends AdminDao
{
  override type Selector = BSONDocument

  override def insert(administrator: Administrator): Future[Administrator] =
    mongoDao.insert(administrator)

  override def find(selector: BSONDocument): Future[List[Administrator]] =
    mongoDao.find(selector)

  override def getByUsername(username: String): OptionT[Future, Administrator] =
    OptionT {
      for {
        result <- mongoDao.findByKeyValue("username" -> username)
      }
      yield result.headOption
    }
}

object MongoAdminDao
{
  import BSONDocumentHandlers._

  val COLLECTION_NAME = "administrators"

  def apply(mongoUri: String)(implicit executionContext: ExecutionContext): Future[MongoAdminDao] =
    for {
      collection <- MongoDataAccessObject.collection(mongoUri, COLLECTION_NAME)
    }
    yield new MongoAdminDao(new MongoDataAccessObject[Administrator](collection))
}