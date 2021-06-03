package uz.scala.telegram.bot

import uz.scala.telegram.bot.api.{TelegramBotApi, Update}
import uz.scala.telegram.bot.http.ScalajHttpClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._

/**
 * TelegramBot
 *
 * Base for Telegram Bots
 */
abstract class TelegramBot(val token: String) extends TelegramBotApi(token) with ScalajHttpClient {

  lazy val botName: String = Await.result(getMe.map(_.username.get), 5.seconds)

  /**
   * handleUpdate
   *
   * Process incoming updates (comming from polling, webhooks...)
   */
  def handleUpdate(update: Update): Unit
}
