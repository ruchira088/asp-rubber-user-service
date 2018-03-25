package com.ruchij.daos

import com.ruchij.models.Customer

import scala.concurrent.Future

trait CustomerDao
{
  def insert(customer: Customer): Future[Customer]

  def find(selector: Customer => Boolean): Future[List[Customer]]
}
