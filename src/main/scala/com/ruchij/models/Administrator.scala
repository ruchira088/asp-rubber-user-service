package com.ruchij.models

import org.joda.time.DateTime

case class Administrator(
    id: String,
    createAt: DateTime,
    username: String,
    password: Option[String],
    email: String,
    firstName: String,
    lastName: Option[String]
) {
  self =>

  def sanitize: Administrator = self.copy(password = None)
}
