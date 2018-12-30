package amanda

import Common._

object Main extends App {

  val startAmanda = Amanda(50, false)
  val startRa9 = Ra9(0, false)
  val startState = GameState("start", startAmanda, startRa9)

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
