package com.prince.api

import cats.effect.Sync
import com.bot4s.telegram.models.ChatType._
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.multipart.Multipart
import telegram.bot.Bot

object Routes {
  def helloWorldRoutes[F[_] : Sync]: HttpRoutes[F] = {
    val bot = new Bot("801181530:AAHewjnuAhU40jYF_k0G5rtbuWg9WfbR4_A")
    bot.run()
    //-1001397860592 -scala group
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root =>
        bot.sendMessage("Hello world!", -1001397860592L, Supergroup)
        Ok("Hello world!")
      case req @ POST -> Root / "multipart" =>
        req.decode[Multipart[F]] { m =>
          Ok(
            s"""Multipart Data\nParts:${m.parts.length}
               |${m.parts.map(f => f.name + ", headers: " + f.headers).mkString("\n")}""".stripMargin)
        }
    }
  }
}