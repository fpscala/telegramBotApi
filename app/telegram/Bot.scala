package telegram

import cats.implicits.{catsStdInstancesForFuture, toFunctorOps}
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.FutureSttpClient
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.methods.{SendDocument, SendPhoto}
import com.bot4s.telegram.models._
import sttp.capabilities
import sttp.capabilities.akka.AkkaStreams
import sttp.client3.SttpBackend
import sttp.client3.akkahttp.AkkaHttpBackend

import java.nio.file.Files
import java.util.Date
import scala.concurrent.Future

//1001397860592
class Bot(token: String) extends TelegramBot
  with Polling
  with Commands[Future] {
  implicit val backend: SttpBackend[Future, AkkaStreams with capabilities.WebSockets] = AkkaHttpBackend()
  override val client: RequestHandler[Future] = new FutureSttpClient(token)
  var messageId = 5000

  def incrMessageId: Int = {
    messageId += 2
    messageId
  }

  def currentTime: Int = new Date().getTime.toInt

  //  def sendMessage(id: Long, text: String, chatType: ChatType): Future[Unit] = {
  //    implicit val message: Message = Message(incrMessageId, None, currentTime, Chat(id, chatType))
  //    reply(text).void
  //  }

  def sendFile(id: Long, file: java.io.File, caption: Option[String] = None): Future[Message] = {
    println(file.getName)
    val inputFile = InputFile(file.getName, Files.readAllBytes(file.toPath))
    request(SendDocument(-id, inputFile, caption))
  }

  onCommand("single") { implicit msg =>
    println(msg.source)
    request(SendPhoto(msg.source, InputFile("AgACAgIAAxkBAAIBmF5NlVQVb6iPrSZWAonh4PnZm_GMAALtrjEbORhoSkLApyEUFBhoPA1xkS4AAwEAAwIAA3gAA5wVAAIYBA"))).void
  }

  onCommand("start") { implicit msg =>
    reply("ddd").void
  }
}