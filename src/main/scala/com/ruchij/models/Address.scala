package com.ruchij.models

import com.ruchij.constants.ConfigValues.DEFAULT_COUNTRY

case class Address(
    line_1: String,
    line_2: Option[String],
    suburb: String,
    postCode: Int,
    state: State,
    country: String = DEFAULT_COUNTRY
)
