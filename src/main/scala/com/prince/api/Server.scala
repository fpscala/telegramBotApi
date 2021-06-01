package com.prince.api

import cats.effect.{ConcurrentEffect, ExitCode, Timer}
import fs2.Stream
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

import scala.concurrent.ExecutionContext.global
import scala.language.higherKinds

object Server {

  def stream[F[_] : ConcurrentEffect](implicit T: Timer[F]): Stream[F, ExitCode] = {
    /**
      * Combine Service Routes into an HttpApp.
      * Can also be done via a Router if you
      * want to extract a segments not checked
      * in the underlying routes.
      * */
    val httpApp = Routes.helloWorldRoutes[F].orNotFound

    /**
      * With Middlewares in place
      * */
    val finalHttpApp = Logger.httpApp(logHeaders = true, logBody = true)(httpApp)
    for {
      exitCode <- BlazeServerBuilder[F](global)
        .bindHttp(9009, "0.0.0.0")
        .withHttpApp(finalHttpApp)
        .serve
    } yield exitCode
  }
}
