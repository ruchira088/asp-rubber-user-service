package com.ruchij.web

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.eed3si9n.ruchij.BuildInfo
import com.ruchij.EnvironmentVariables
import com.ruchij.constants.ConfigValues._
import com.ruchij.constants.EnvValueNames._
import com.ruchij.daos.impl.mongo.MongoAdminDao
import com.ruchij.ecs.BlockingExecutionContext
import com.ruchij.services.AdminService
import com.ruchij.services.hashing.BlowfishPasswordHashing
import com.ruchij.utils.ConfigUtils.getEnvValue
import com.ruchij.web.routes.IndexRoute
import com.typesafe.scalalogging.Logger

import scala.concurrent.Future.fromTry
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, Promise}
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

    val result: Future[ServerBinding] =
      for {
        mongoUri <- fromTry(getEnvValue(MONGO_URI))

        adminDao <- MongoAdminDao(mongoUri)
        blockingExecutionContext = BlockingExecutionContext()
        passwordHashingService = BlowfishPasswordHashing(blockingExecutionContext)

        adminService = AdminService(adminDao, passwordHashingService)

        serverBinding <- Http().bindAndHandle(IndexRoute(adminService), HTTP_INTERFACE, httpPort())
      }
      yield serverBinding

    result
        .onComplete {
          case Success(ServerBinding(localAddress)) => {
            logger.info(s"Server is listening on port ${localAddress.getPort}...")
          }
          case Failure(NonFatal(throwable)) => {
            logger.error(throwable.getMessage)
            System.exit(1)
          }
        }

    Await.ready(Promise[Unit].future, Duration.Inf)
  }

  def httpPort()(implicit environmentVariables: EnvironmentVariables): Int =
    getEnvValue(HTTP_PORT)
      .flatMap(httpPortString => Try(httpPortString.toInt))
      .getOrElse(DEFAULT_HTTP_PORT)

  def add(x: Int, y: Int): Int = x + y
}
