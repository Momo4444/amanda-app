package amanda

import Common._

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
