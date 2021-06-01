package telegram.bot

import cats.implicits.toFunctorOps
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.models.ChatType.Private
import com.bot4s.telegram.models._
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}

import scala.concurrent.Future
import scala.language.higherKinds

/** Generates random values.
 */
class Bot(val token: String) extends TelegramBot
  with Polling
  with Commands[Future] {

  LoggerConfig.factory = PrintLoggerFactory()
  // set log level, e.g. to DEBUG
  LoggerConfig.level = LogLevel.DEBUG

  override val client: RequestHandler[Future] = new ScalajHttpClient(token)

  def sendMessage(text: String): Future[Unit] = {
    val chat: Chat = Chat(124720112L, Private)
    val message: Message = Message(4034, None, 1622549948, chat)

    reply(text)(message).void
  }
}