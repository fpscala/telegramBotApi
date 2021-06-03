package uz.scala.telegram.bot

import com.sun.net.httpserver._
import uz.scala.telegram.bot.api.Update
import uz.scala.telegram.bot.json.JsonUtils

import java.net.InetSocketAddress

/**
 * Webhooks
 *
 * Provides updates based on webhooks
 * The server once stopped cannot be restarted
 */
trait Webhooks extends Runnable with HttpHandler {
  this: TelegramBot =>

  val webHookUrl: String

  val hostname: String = "localhost"
  val port    : Int    = 1234
  val backlog : Int    = 0

  private val address = new InetSocketAddress(hostname, port)
  private val server  = HttpServer.create(address, backlog)

  import OptionPimps._

  override def run(): Unit = {
    server.createContext("/", this)
    server.start()
    setWebhook(webHookUrl)
  }

  private def respond(exchange: HttpExchange, code: Int = 200, body: String = "") {
    val bytes = body.getBytes
    exchange.sendResponseHeaders(code, bytes.size)
    val out = exchange.getResponseBody
    out.write(bytes)
    out.write("\r\n\r\n".getBytes)
    out.close()
    exchange.close()
  }

  override def handle(exchange: HttpExchange): Unit = {
    val method = exchange.getRequestMethod
    val path   = exchange.getRequestURI.getPath
    println(method + " " + path)
    try {
      // the token must contained somewhere in the path to 'authenticate' the request
      if (method == "POST" && path.contains(token)) {
        val body = scala.io.Source.fromInputStream(exchange.getRequestBody).mkString
        handleUpdate(JsonUtils.unjsonify[Update](body))
        respond(exchange, body = "OK!")
      } else
      // NOT FOUND
        respond(exchange, 404)
    } catch {
      case ex: Exception => respond(exchange, 500, ex.toString)
    }
  }

  def stop(delay: Int = 1): Unit = {
    setWebhook(None)
    server.stop(delay)
  }
}
