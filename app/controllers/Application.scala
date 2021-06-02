package controllers

import com.bot4s.telegram.models.ChatType.Private
import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.libs.Files.TemporaryFile
import play.api.libs.Files.TemporaryFile.temporaryFileToFile
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import telegram.Bot

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class Application @Inject()(val controllerComponents: ControllerComponents,
                            val configuration: Configuration)
                           (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {
  val token: String = configuration.get[String]("telegram-bot.token")
  val bot = new Bot(token)
  bot.run()

  def index: Action[AnyContent] = Action {
    Ok(views.html.index())
  }

  def sendFile: Action[MultipartFormData[TemporaryFile]]
  = Action.async(parse.multipartFormData) { implicit request =>
    try {
      val body = request.body.asFormUrlEncoded
      println(body.toList)
      val chatId = body("chatId").head.toLong
      println(chatId)
      val caption = body("caption").headOption
      println(caption)
      request.body.file("file") match {
        case Some(file) =>
          bot.sendFile(chatId, temporaryFileToFile(file.ref), caption).map{ _ =>
            Ok("OK")
          }
        case None =>
          Future.successful(BadRequest("Missing File!"))
      }
    } catch {
      case error: Throwable =>
        println(error)
      Future.successful(BadRequest("Something went wrong!"))
    }
  }

  def sendMessage: Action[JsValue] = Action.async(parse.json) { implicit request =>
    Try {
      val chatId = (request.body \ "chatId").as[Long]
      val message = (request.body \ "message").as[String]
//      bot.sendMessage(chatId, message, Private).map{ _ =>
//        Ok(Json.toJson("OK"))
//      }
      Future.successful(BadRequest(Json.toJson("Something went wrong!")))

    } getOrElse {
      Future.successful(BadRequest(Json.toJson("Something went wrong!")))
    }
  }
}
