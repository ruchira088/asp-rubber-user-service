package com.ruchij.web.requests.validations

trait ItemValidator[A]
{
  def validate(item: A): List[IllegalArgumentException]
}

object ItemValidator
{
  def validate(condition: Boolean, validationErrorMessage: String): Option[IllegalArgumentException] =
    if (condition) None else Some(new IllegalArgumentException(validationErrorMessage))

  def nonEmpty(stringValue: String, variableName: String) =
    validate(stringValue.trim.nonEmpty, s"$variableName CANNOT be EMPTY.")
}
