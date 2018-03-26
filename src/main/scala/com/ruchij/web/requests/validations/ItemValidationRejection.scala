package com.ruchij.web.requests.validations

import akka.http.scaladsl.server.Rejection

case class ItemValidationRejection(validationErrors: List[IllegalArgumentException])
  extends Rejection