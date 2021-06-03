package telegram

import uz.scala.telegram.bot.{Commands, Polling, TelegramBot}
import uz.scala.telegram.bot.api.{InputFile, Message}

import java.io.File
import java.util.Date
import scala.concurrent.Future

//1001397860592
class Bot(token: String) extends TelegramBot(token) with Polling with Commands {
  var messageId = 5000

  def incrMessageId: Int = {
    messageId += 2
    messageId
  }

  def currentTime: Int = new Date().getTime.toInt

  def sendFile(id: Long, fileName: String, file: File, caption: Option[String] = None): Future[Message] = {
    sendDocument(id, InputFile(fileName, file), caption)
  }

  onCommand("start") { (sender, _) =>
    println(s"Chat id: $sender")
    sendPhoto(sender, InputFile("./image.jpeg"), caption = Some("💪!!!🦾"))
  }
}