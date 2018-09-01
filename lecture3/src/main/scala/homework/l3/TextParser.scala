package homework.l3

case class BookRegex(title: String,
                     author: String,
                     genre: String,
                     height: Int,
                     publisher: String)

object TextParser extends App {
  def parse(text: String): Seq[BookRegex] = {
    val regExpression = "(.+),\"(.+)\",(.+),([0-9]+),(.+)".r
    val bookcase = scala.collection.mutable.ListBuffer.empty[BookRegex]
    val textSplit = text.split("\\R")
    textSplit.foreach { x =>
      x match {
        case regExpression(title, author, genre, height, publisher) =>
          bookcase += BookRegex(title, author, genre, height.toInt, publisher)
        case _ =>
      }
    }
    bookcase
  }
}