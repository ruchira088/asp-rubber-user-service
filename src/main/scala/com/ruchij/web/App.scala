package com.ruchij.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.eed3si9n.ruchij.BuildInfo
import com.ruchij.EnvironmentVariables
import com.ruchij.constants.ConfigValues._
import com.ruchij.constants.EnvValueNames._
import com.ruchij.utils.ConfigUtils
import com.ruchij.web.routes.IndexRoute
import com.typesafe.scalalogging.Logger

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Promise}
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}

object App
{
  val logger: Logger = Logger(App.getClass)

  def main(args: Array[String]): Unit =
  {
    implicit val actorSystem: ActorSystem = ActorSystem(BuildInfo.name)
    implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
    implicit val executionContext: ExecutionContext = actorSystem.dispatcher

    implicit val environmentVariables: EnvironmentVariables = sys.env

    Http().bindAndHandle(IndexRoute(), HTTP_INTERFACE, httpPort())
        .onComplete {
          case Success(serverBinding) => {
            logger.info(s"Server is listening on port ${serverBinding.localAddress.getPort}...")
          }
          case Failure(NonFatal(throwable)) => {
            logger.error(throwable.getMessage)
            System.exit(1)
          }
        }

    Await.ready(Promise[Unit].future, Duration.Inf)
  }

  def httpPort()(implicit environmentVariables: EnvironmentVariables): Int =
    ConfigUtils.getEnvValue(HTTP_PORT)
      .flatMap(httpPortString => Try(httpPortString.toInt))
      .getOrElse(DEFAULT_HTTP_PORT)

  def add(x: Int, y: Int): Int = x + y
}
