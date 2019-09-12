package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

case class Comment(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS, entity: String = "Amanda") extends Prompt {

  private implicit val promptList = Config.gameState.promptList

  override def cycle(gs: GameState): GameState = {
    print(gs)
    inputLoop
    val nextPromptKey = keywords.head
    val preProtocolGS = gs.changeGameState(getPrompt(nextPromptKey).deltaGS).updatePromptKey(nextPromptKey)
    val postDeviancyProtocolGS = deviancyProtocol(preProtocolGS, gs.oldPromptKey)
    val postAmandaKnowsProtocolGS = amandaKnowsProtocol(postDeviancyProtocolGS, nextPromptKey)
    postAmandaKnowsProtocolGS.prompt.cycle(postAmandaKnowsProtocolGS)
    //    val newGS = postAmandaKnowsProtocolGS.changeGameState(getPrompt(nextPromptKey).deltaGS).updatePromptKey(nextPromptKey)
    //    newGS.prompt.cycle(newGS)
  }

  override def print(gs: GameState): Unit = {
    val formattedMessage = formatMessage(message, gs.printWidth)
    println(
      s"""
         |${gs.scrollScreen}
         |${gs.divider}
         |${entity}:
         |
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

class TestComment(message: String, keywords: List[String], deltaGS: DeltaGameState = sameGS, entity: String = "Amanda")
  extends Comment(message, keywords, deltaGS, entity) {
  override def readInput: String = ""
}
