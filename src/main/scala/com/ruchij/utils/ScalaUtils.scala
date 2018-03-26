package com.ruchij.utils

object ScalaUtils
{
  def reduceOptions[A](options: Option[A]*): List[A] =
    options.foldLeft(List.empty[A]) {
      case (list, Some(value)) => list :+ value
      case (list, _) => list
    }
}
