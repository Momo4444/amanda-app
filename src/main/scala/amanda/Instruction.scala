package amanda

import amanda.Common._

case class Instruction(message: String, keywords: List[String]) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    print(gs)
    val nextPromptKey = inputLoop
    gs.updateGameState(newPromptKey = nextPromptKey).cycle
  }

  override def print(gs: GameState): Unit = {
    val formattedMessage = formatMessage(message, gs.width)
    println(
      s"""
         |${gs.scrollScreen}
         |${gs.divider}
         |${formattedMessage}
         |${gs.divider}
         |${gs.meters}
         |${gs.divider}
      """.stripMargin)
  }

  override def inputLoop: String = {
    val input = readInput
    if (checkInput(input, keywords)) input
    else {
      println("That is not the correct answer. Try again.")
      inputLoop
    }
  }

  override def checkInput(input: String, keywords: List[String]): Boolean = keywords.contains(input)

}
