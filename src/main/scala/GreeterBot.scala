import cats.instances.future._
import cats.syntax.functor._
import com.bot4s.telegram.api.RequestHandler
import com.bot4s.telegram.api.declarative.Commands
import com.bot4s.telegram.clients.ScalajHttpClient
import com.bot4s.telegram.future.{Polling, TelegramBot}
import com.bot4s.telegram.methods.ParseMode.Markdown
import slogging.{LogLevel, LoggerConfig, PrintLoggerFactory}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/** Generates random values.
 */
object GreeterBot extends App {

  // To run spawn the bot
  val bot = new GreeterBot("asdasd")
  val eol = bot.run()
  println("Press [ENTER] to shutdown the bot, it may take a few seconds...")
  scala.io.StdIn.readLine()
  bot.shutdown() // initiate shutdown
  // Wait for the bot end-of-life
  Await.result(eol, Duration.Inf)
}

class GreeterBot(val token: String) extends TelegramBot
  with Polling
  with Commands[Future] {

  LoggerConfig.factory = PrintLoggerFactory()
  // set log level, e.g. to TRACE
  LoggerConfig.level = LogLevel.TRACE

  override val client: RequestHandler[Future] = new ScalajHttpClient(token)

  val rng = new scala.util.Random(System.currentTimeMillis())

  //  onCommand("/dice" | "roll") { implicit msg =>
  //    reply("⚀⚁⚂⚃⚄⚅"(rng.nextInt(6)).toString).void
  //  }


  //  override def receiveMessage(msg: Message): Future[Unit] =
  //    msg.text.fold(Future.successful(())) { text =>
  //      logger.debug(s"-------- text: $text")
  //      request(SendMessage(msg.source, text.reverse)).void
  //    }

  onMessage { implicit msg =>
    msg.text.fold(Future.successful(())) { text =>
      println(s"-------- text: $text")
      reply("Siz yubargan habar uchun palindrom: \n" + text + text.reverse.tail, Some(Markdown)).void
    }
  }

}