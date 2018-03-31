package com.ruchij.services

import com.ruchij.daos.AdminDao
import com.ruchij.models.Administrator
import com.ruchij.services.hashing.PasswordHashingService
import com.ruchij.utils.GeneralUtils.uuid
import com.ruchij.web.requests.CreateAdminRequest
import org.joda.time.DateTime

import scala.concurrent.{ExecutionContext, Future}

class AdminService(adminDao: AdminDao, passwordHashingService: PasswordHashingService)
                  (implicit executionContext: ExecutionContext)
{
  def create(createAdminRequest: CreateAdminRequest): Future[Administrator] =
    for {
      hashedPassword <- passwordHashingService.hashPassword(createAdminRequest.password)

      admin <- adminDao.insert {
        Administrator(
          uuid(),
          DateTime.now(),
          createAdminRequest.username,
          Some(hashedPassword),
          createAdminRequest.email,
          createAdminRequest.firstName,
          createAdminRequest.lastName
        )
      }
    }
    yield admin
}

object AdminService
{
  def apply(adminDao: AdminDao, passwordHashingService: PasswordHashingService)
           (implicit executionContext: ExecutionContext): AdminService =
    new AdminService(adminDao, passwordHashingService)
}
