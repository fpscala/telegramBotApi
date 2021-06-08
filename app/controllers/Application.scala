package controllers

import com.typesafe.scalalogging.LazyLogging
import play.api.Configuration
import play.api.libs.Files.TemporaryFile
import play.api.libs.Files.TemporaryFile.temporaryFileToFile
import play.api.mvc._
import telegram.Bot

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class Application @Inject()(val controllerComponents: ControllerComponents,
                            val configuration: Configuration)
                           (implicit val ec: ExecutionContext)
  extends BaseController with LazyLogging {
  val token: String = configuration.get[String]("telegram-bot.token")
  val bot = new Bot(token)

  def index: Action[AnyContent] = Action {
    Ok("OK")
  }

  def sendFile: Action[MultipartFormData[TemporaryFile]]
  = Action.async(parse.multipartFormData) { implicit request =>
    try {
      val body = request.body.asFormUrlEncoded
      val chatId = body("chatId").head.toLong
      val caption = body("caption").headOption
      request.body.file("file") match {
        case Some(file) =>
          bot.sendFile(chatId, file.filename, temporaryFileToFile(file.ref), caption).map { _ =>
            Ok("OK")
          }
        case None =>
          Future.successful(BadRequest("Missing File!"))
      }
    } catch {
      case error: Throwable =>
        logger.error("Error occurred while send file:", error)
        Future.successful(BadRequest("Something went wrong!"))
    }
  }
}
