package com.ruchij.ecs

import scala.concurrent.ExecutionContext

trait ExecutionContextProvider
{
  implicit def executionContext: ExecutionContext
}
