package com.ruchij.daos

import scala.concurrent.Future

trait DataAccessObject[T]
{
  type Selector

  def insert(value: T): Future[T]

  def find(selector: Selector): Future[List[T]]
}