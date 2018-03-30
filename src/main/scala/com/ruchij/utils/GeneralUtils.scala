package com.ruchij.utils

object GeneralUtils
{
  def normalize(string: String): String =
    string.map(_.toLower).replaceAll(" ", "-")
}