package com.ruchij.services.hashing

import com.ruchij.ecs.BlockingExecutionContext
import org.mindrot.jbcrypt.BCrypt

import scala.concurrent.{ExecutionContext, Future}

class BlowfishPasswordHashing()(implicit executionContext: ExecutionContext)
  extends PasswordHashingService
{
  override def hashPassword(password: String): Future[String] =
    Future {
      BCrypt.hashpw(password, BCrypt.gensalt())
    }

  override def checkPassword(hashedPassword: String, candidate: String): Future[Boolean] =
    Future {
      BCrypt.checkpw(candidate, hashedPassword)
    }
}

object BlowfishPasswordHashing
{
  def apply(blockingExecutionContext: BlockingExecutionContext) =
    new BlowfishPasswordHashing()(blockingExecutionContext)
}
