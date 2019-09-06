package amanda.model.prompts

import amanda.Common._
import amanda.{DeltaGameState, GameState}

case class Instruction(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    print(gs)
    val nextPromptKey = inputLoop
    val newGS = gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS)
    val deviancyProtocolGS = if (deltaGS.deltaRa9.deltaIsDeviant) newGS.runDeviancyProtocol else newGS
    deviancyProtocolGS.prompt.cycle(deviancyProtocolGS)
  }

  override def print(gs: GameState): Unit = {
    val formattedMessage = formatMessage(message, gs.printWidth)
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
    if (checkInput(input, "deviant" :: keywords)) input
    else {
      println("That is not the correct answer. Try again.")
      inputLoop
    }
  }

}
