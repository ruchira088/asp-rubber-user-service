package com.ruchij.daos

import com.ruchij.models.Administrator
import scalaz.OptionT

import scala.concurrent.Future

trait AdminDao extends DataAccessObject[Administrator]
{
  def getByUsername(username: String): OptionT[Future, Administrator]
}
