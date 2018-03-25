package com.ruchij.models

import scala.util.Try

case class Telephone(number: String)

object Telephone
{
  def apply(number: String): Try[Telephone] =
    if (number.forall(isTelephoneDigit)) ??? else ???

  def isTelephoneDigit: PartialFunction[Char, Boolean] =
  {
    case '+' => true
    case ' ' => true
    case '-' => true
    case char if char.isDigit => true
    case _ => false
  }
}