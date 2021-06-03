package uz.scala.telegram.bot

object Utils {
  def tokenFromFile(file: String): String = {
    val source = scala.io.Source.fromFile(file)
    val token  = source.getLines().next()
    source.close()
    token
  }
}
