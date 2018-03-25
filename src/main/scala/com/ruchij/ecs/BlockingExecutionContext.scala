package com.ruchij.ecs

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

trait BlockingExecutionContext extends ExecutionContext

object BlockingExecutionContext
{
  def apply()(implicit actorSystem: ActorSystem) =
    BlockingExecutionContextImpl()
}