package com.ruchij.daos

import scala.concurrent.Future

trait DataAccessObject[T]
{
  def insert(value: T): Future[T]

  def find(selector: T => Boolean): Future[List[T]]
}