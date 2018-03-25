package com.ruchij.models

import org.joda.time.DateTime

case class Customer(
    id: String,
    createAt: DateTime,
    firstName: String,
    lastName: Option[String],
    telephone: String,
    email: Option[String],
    address: Address
)