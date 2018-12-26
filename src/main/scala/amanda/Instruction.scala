package amanda

import amanda.Common._

case class Instruction(gameState: GameState) extends Prompt {

  override def cycle(gs: GameState = gameState): GameState = {
    print(gs)
    val newPromptKey = inputLoop(gs)
    gs.updatePromptKey(newPromptKey).cycle
  }

  override def print(gs: GameState = gameState): Unit = {
    val formattedMessage = formatMessage(gs.message, gs.width)
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

  override def inputLoop(gs: GameState): String = {
    val input = readInput.toLowerCase()
    if (checkInput(input, gs.keywords)) input
    else {
      println("That is not the correct answer. Try again.")
      inputLoop(gs)
    }
  }

  override def checkInput(input: String, keywords: List[String]): Boolean = {
    keywords.contains(input)
  }

}
