package amanda.model.prompts

import amanda.Common.{formatMessage, sameGS}
import amanda.model.{DeltaGameState, GameState}

case class Terminus(message: String, keywords: List[String] = Nil, deltaGS: DeltaGameState = sameGS) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    print(gs)
    inputLoop
    gs
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

class TestTerminus(message: String, keywords: List[String] = Nil, deltaGS: DeltaGameState = sameGS)
  extends Terminus(message, keywords, deltaGS) {
  override def readInput: String = ""
}
