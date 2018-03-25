package com.ruchij.services.hashing

import scala.concurrent.Future

trait PasswordHashingService
{
  def hashPassword(password: String): Future[String]

  def checkPassword(hashedPassword: String, candidate: String): Future[Boolean]
}
