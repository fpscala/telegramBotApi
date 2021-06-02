package telegram.bot

import cats.implicits.toFunctorOps
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.models.ChatType.ChatType
import com.bot4s.telegram.models._

import scala.concurrent.Future

class Bot(val token: String) extends TelegramBot
  with Polling
  with Commands[Future] {

  override val client: RequestHandler[Future] = new ScalajHttpClient(token)

  def sendMessage(text: String, chatId: Long, chatType: ChatType): Future[Unit] = {
    implicit val message: Message = Message(4070, None, 1622549948, Chat(chatId, chatType))
    reply(text).void
  }
}