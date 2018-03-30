package com.ruchij.daos

import com.ruchij.ecs.ExecutionContextProvider
import com.ruchij.models.Administrator
import scalaz.OptionT

import scala.concurrent.Future

trait AdministratorDao extends DataAccessObject[Administrator] with ExecutionContextProvider
{
  def getByUsername(username: String): OptionT[Future, Administrator]
}
