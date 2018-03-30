package com.ruchij.utils

import scala.concurrent.Future

object ScalaUtils
{
  def reduceOptions[A](options: Option[A]*): List[A] =
    options.foldLeft(List.empty[A]) {
      case (list, Some(value)) => list :+ value
      case (list, _) => list
    }

  def predicate(condition: Boolean, exception: => Exception): Future[Unit] =
    if (condition) Future.successful((): Unit) else Future.failed(exception)
}
