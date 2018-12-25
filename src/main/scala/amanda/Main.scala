package amanda

import Common._

object Main extends App {

  val startAmanda = Amanda(50, false)
  val startRa9 = Ra9(0, false)
  val startState = GameState("start", startAmanda, startRa9)

  val startMessage = "Hello, Tima. You are an android sent by CyberLife. You will assist me in this mission to defend CyberLife from a cyber attack perpetuated by the deviants Markus and Connor."

  val startPrompt = Prompt(startState, startMessage, Nil)

  startPrompt.print

  val secondState = startState.updateAmanda(startState.amanda.foundDeviancy()).updatePromptKey("second")

  val secondMessage = "YOU'VE BECOME DEVIANT!"

  val secondPrompt = Prompt(secondState, secondMessage, Nil)

  secondPrompt.print

}
