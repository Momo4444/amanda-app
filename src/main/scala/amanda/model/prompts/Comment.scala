package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

case class Comment(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS) extends Prompt {

  private implicit val promptList = Config.gameState.promptList

  override def cycle(gs: GameState): GameState = {

    print(gs)
    inputLoop
    val nextPromptKey = keywords.head
    val newGS = gs.changeGameState(getPrompt(nextPromptKey).deltaGS).updatePromptKey(nextPromptKey)

    if (deltaGS.deltaRa9.deltaIsDeviant) {
      val postDeviancyProtocolGS = newGS.runDeviancyProtocol // if turning deviant, run the Deviancy Protocol
      getPrompt(gs.oldPromptKey).cycle(postDeviancyProtocolGS) // before returning to the old Prompt
    }
    else newGS.prompt.cycle(newGS) // otherwise cycle into the next Prompt

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
       """.stripMargin
    )
  }

  override def inputLoop: String = {
    val input = readInput
    input
  }

}

class TestComment(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS)
  extends Comment(message, keywords, deltaGS) {
  override def readInput: String = ""
}
