package amanda

import scala.io.StdIn
import Common._

case class GameState(promptKey: String, keywords: List[String], amanda: Amanda, ra9: Ra9) {

  def updatePromptKey(newPromptKey: String): GameState = GameState(newPromptKey, keywords, amanda, ra9)

  def updateAmanda(newAmanda: Amanda): GameState = GameState(promptKey, keywords, newAmanda, ra9)

  def updateRa9(newRa9: Ra9): GameState = GameState(promptKey, keywords, amanda, newRa9)

  val message = prompts(promptKey)
  val meters = s"Amanda: ${amanda.meter}%,   Software instability: ${ra9.softwareInstability}%"
  val amandaMeter = s"Amanda: ${amanda.meter}%"
  val softwareInstability = s"Software instability: ${ra9.softwareInstability}%"
  val width = if (meters.length < 50) 50 else meters.length
  val divider = "-" * (width + 5)
  val scrollScreen = "\n" * 40

  def cycle: GameState = {
    print
    val newPromptKey = inputLoop
    updatePromptKey(newPromptKey).cycle
  }

  def print: Unit = {
    val formattedMessage = GameState.formatMessage(message, width)
    println(
      s"""
         |${scrollScreen}
         |${divider}
         |${formattedMessage}
         |${divider}
         |${meters}
         |${divider}
      """.stripMargin)
  }

  def inputLoop: String = {
    val input = readInput.toLowerCase()
    if (checkInput(input, keywords)) input
    else {
      println("That is not the correct answer. Try again.")
      inputLoop
    }
  }

  def readInput: String = StdIn.readLine()

  def checkInput(input: String, keywords: List[String]): Boolean = keywords.exists(keyword => keyword == input)

//  def checkInput(input: String, keywords: List[String]): String = {
//    val check = keywords.filter(keyword => keyword == input)
//    if (check.length == 1) nextPrompt(check.head)
//    else println("That is not the correct answer. Try again."); readInput
//  }

  def nextPrompt(nextPromptKey: String): GameState = {
    updatePromptKey(nextPromptKey)
  }

}

object GameState {

  def formatMessage(message: String, width: Int): String = {
    val chars = message.toList
    def indent(chars: List[Char], i: Int): List[Char] = {
      if (i >= chars.length) chars
      else if (i % width == 0) {
        def indent2(chars: List[Char], i: Int, j: Int): List[Char] = {
          val k = i - j
          if (chars(k) == ' ') chars.updated(k, '\n')
          else indent2(chars, i, j + 1)
        }
        indent(indent2(chars, i, 0), i + 1)
      }
      else indent(chars, i + 1)
    }
    indent(chars, 1).mkString("")
  }

}
