package com.ruchij.utils

import com.ruchij.EnvironmentVariables
import com.ruchij.exceptions.EnvValueUndefinedException

import scala.util.{Failure, Success, Try}

object ConfigUtils
{
  def getEnvValue(name: String)(implicit environmentVariables: EnvironmentVariables): Try[String] =
    environmentVariables.get(name)
      .fold[Try[String]](Failure(EnvValueUndefinedException(name)))(Success(_))
}
