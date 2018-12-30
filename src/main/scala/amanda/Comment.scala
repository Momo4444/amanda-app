package amanda

import amanda.Common._
import scala.io.StdIn

case class Comment(message: String, keywords: List[String], deltaGS: DeltaGameState) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    print(gs)
    inputLoop
    val nextPromptKey = keywords.head
    gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS).cycle
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
       """.stripMargin
    )
  }

  override def inputLoop: String = {
    val input = readInput
    input
  }

}
