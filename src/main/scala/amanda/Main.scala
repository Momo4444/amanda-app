package amanda

import Common._

object Main extends App {

  val startAmanda = Amanda(50, false)
  val startRa9 = Ra9(0, false)
  val startState = GameState("start", List("second"), startAmanda, startRa9)

  startState.cycle

}
