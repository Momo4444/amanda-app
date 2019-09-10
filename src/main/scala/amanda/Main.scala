package amanda

import amanda.model.{Amanda, GameState, Ra9}

object Main extends App {

  private val startPrompt = Config.gameState.startPrompt
  private val amandaStartValue = Config.amanda.startValue
  private val ra9StartValue = Config.ra9.startValue

  val startAmanda = Amanda(amandaStartValue, false)
  val startRa9 = Ra9(ra9StartValue, false)
  val startState = GameState(startPrompt, startAmanda, startRa9, "")

  startState.prompt.cycle(startState)

}
