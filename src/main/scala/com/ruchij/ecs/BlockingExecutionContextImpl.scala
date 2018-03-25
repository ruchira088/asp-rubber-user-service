package com.ruchij.ecs

import akka.actor.ActorSystem
import akka.dispatch.MessageDispatcher

class BlockingExecutionContextImpl(messageDispatcher: MessageDispatcher)
  extends BlockingExecutionContext
{
  override def execute(runnable: Runnable): Unit =
    messageDispatcher.execute(runnable)

  override def reportFailure(cause: Throwable): Unit =
    messageDispatcher.reportFailure(cause)
}

object BlockingExecutionContextImpl
{
  def apply()(implicit actorSystem: ActorSystem) =
    new BlockingExecutionContextImpl(actorSystem.dispatchers.lookup(BLOCKING_DISPATCHER_NAME))

  val BLOCKING_DISPATCHER_NAME = "blocking-dispatcher"
}
