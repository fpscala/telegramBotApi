package telegram

import uz.scala.telegram.bot.api.{InputFile, Message}
import uz.scala.telegram.bot.{Commands, Polling, TelegramBot}

import java.io.File
import scala.concurrent.Future

class Bot(token: String) extends TelegramBot(token) with Polling with Commands {

  def sendFile(id: Long, fileName: String, file: File, caption: Option[String] = None): Future[Message] = {
    sendDocument(id, InputFile(fileName, file), caption, parseMode = ParseMode.HTML)
  }

  onCommand("start") { (sender, _) =>
    println(s"Chat id: $sender")
  }
}