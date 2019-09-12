package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

case class Instruction(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS) extends Prompt {

  private implicit val promptList = Config.gameState.promptList
  private val deviancyPrompt = Config.ra9.deviancyPrompt

  override def cycle(gs: GameState): GameState = {
    print(gs)
    val nextPromptKey = inputLoop
    val postDeviancyProtocolGS = deviancyProtocol(gs, gs.oldPromptKey)
    val postAmandaKnowsProtocolGS = amandaKnowsProtocol(postDeviancyProtocolGS, nextPromptKey)
    val newGS = postAmandaKnowsProtocolGS.changeGameState(getPrompt(nextPromptKey).deltaGS).updatePromptKey(nextPromptKey)
    newGS.prompt.cycle(newGS)
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
    val allKeywords = if (!Prompt.deviancyProtocolTriggered) deviancyPrompt :: keywords else keywords
    if (Prompt.checkInput(input, allKeywords)) input
    else {
      println("That is not the correct answer. Try again.")
      inputLoop
    }
  }

}

class TestInstruction(mockedInput: String)(message: String, keywords: List[String], deltaGameState: DeltaGameState = sameGS)
  extends Instruction(message, keywords, deltaGameState) {
  override def readInput: String = mockedInput.toLowerCase()
}
