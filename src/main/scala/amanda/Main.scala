package amanda

import Common._

object Main extends App {

  val startAmanda = Amanda(50, false)
  val startRa9 = Ra9(0, false)
  val startState = GameState("start", startAmanda, startRa9)

  startState.print

  val secondState = startState.updateAmanda(startState.amanda.foundDeviancy()).updatePromptKey("second")

  secondState.print

}
