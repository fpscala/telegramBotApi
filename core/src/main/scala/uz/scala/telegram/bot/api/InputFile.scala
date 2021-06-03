package uz.scala.telegram.bot.api

import java.io.{File, FileInputStream, InputStream}
import scala.language.postfixOps

/**
 * Created by mukel on 8/16/15.
 */
trait InputFile {
  val name: String
  val mimeType: String = "application/octet-stream"
  val bytes: Array[Byte]
}

object InputFile {

  def apply(filePath: String): InputFile = apply(new File(filePath))

  def apply(file: File): InputFile = {
    apply(file.getName, new FileInputStream(file))
  }
  def apply(fileName: String, file: File): InputFile = {
    apply(fileName, new FileInputStream(file))
  }

  def apply(fileName: String, inputStream: InputStream): InputFile = new InputFile {
    val name: String = fileName
    val bytes: Array[Byte] = Iterator.continually(inputStream.read()) takeWhile (-1 !=) map (_.toByte) toArray
  }
}