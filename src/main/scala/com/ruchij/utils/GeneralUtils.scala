package com.ruchij.utils

import java.util.UUID

object GeneralUtils
{
  def normalize(string: String): String =
    string.map(_.toLower).replaceAll(" ", "-")

  def uuid(): String = UUID.randomUUID().toString
}