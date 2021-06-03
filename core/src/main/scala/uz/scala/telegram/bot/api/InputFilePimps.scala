package uz.scala.telegram.bot.api

import java.io.File

object InputFilePimps {
  implicit def toInputFile(file: File) : InputFile = InputFile(file)
}
