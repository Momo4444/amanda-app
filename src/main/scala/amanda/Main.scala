package amanda

import Common._
import amanda.model.{Amanda, GameState, Ra9}

object Main extends App {

  private val startPrompt = Config.gameState.startPrompt
  private val amandaStartValue = Config.amanda.startValue
  private val ra9StartValue = Config.ra9.startValue

  val startAmanda = Amanda(amandaStartValue, false)
  val startRa9 = Ra9(ra9StartValue, false)
  val startState = GameState(startPrompt, startAmanda, startRa9, "")

//  for ((k, v) <- prompts) {
//    println(s"Keyword: $k")
//    println(s"Message: ${v.message}")
//    for (s <- v.keywords) println(s)
//    println("\n")
//  }

  startState.prompt.cycle(startState)

}

/* TODO: add protocols
Deviancy protocol: Check GameState.runDeviancyProtocol method + cycle methods of each prompt
                    -> the problem is to get information of the calling prompt keyword list from the current prompt... (eg, get "three" from "deviant" prompt)
                    -> solve in checkInput method of Prompt trait ???

    a map from responses to keywords ???
 */
