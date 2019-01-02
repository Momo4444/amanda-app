package amanda

import amanda.Common._
import scala.io.StdIn

case class Comment(message: String, keywords: List[String], deltaGS: DeltaGameState) extends Prompt {

  def cycle2(gs: GameState): GameState = {
    print(gs)
    inputLoop
    val nextPromptKey = keywords.head
    val newGS = gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS)
    val deviancyProtocolGS = if (deltaGS.deltaRa9.deltaIsDeviant) newGS.runDeviancyProtocol else newGS
    deviancyProtocolGS.cycle
  }

  override def cycle(gs: GameState): GameState = {
    print(gs)
    inputLoop
    val nextPromptKey = keywords.head
    val newGS = gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS)
    val newGS = gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS)
    val deviancyProtocolGS = if (deltaGS.deltaRa9.deltaIsDeviant) newGS.runDeviancyProtocol else newGS
    deviancyProtocolGS.cycle
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
