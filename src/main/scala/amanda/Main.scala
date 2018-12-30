package amanda

import Common._

object Main extends App {

  private val startPrompt = Config.gameState.startPrompt
  private val amandaStartValue = Config.amanda.startValue
  private val ra9StartValue = Config.ra9.startValue

  val startAmanda = Amanda(amandaStartValue, false)
  val startRa9 = Ra9(ra9StartValue, false)
  val startState = GameState(startPrompt, startAmanda, startRa9)

//  for ((k, v) <- prompts) {
//    println(s"Keyword: $k")
//    println(s"Message: ${v.message}")
//    for (s <- v.keywords) println(s)
//    println("\n")
//  }

  startState.cycle

}

/* TODO: add various meter logics in

    a map from responses to keywords ???
 */
