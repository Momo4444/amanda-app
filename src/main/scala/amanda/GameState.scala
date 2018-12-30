package amanda

import scala.io.StdIn
import Common._

case class GameState(promptKey: String, amanda: Amanda, ra9: Ra9) {

  def changeGameState(deltaGS: DeltaGameState): GameState = {
    val deltaAmanda = deltaGS.deltaAmanda
    val deltaRa9 = deltaGS.deltaRa9
    val newDeltaAmanda: DeltaAmanda =
      if (this.ra9.softwareInstability == 0 && deltaGS.deltaRa9.deltaSoftwareInstability <= 0)
        DeltaAmanda(deltaAmanda.deltaMeter + Config.amanda.softwareStabilityIncrement, deltaAmanda.deltaKnowsDeviancy)
      else DeltaAmanda(deltaAmanda.deltaMeter, deltaAmanda.deltaKnowsDeviancy)
    this.updateAmanda(amanda.updateAmanda(newDeltaAmanda)).updateRa9(ra9.updateRa9(deltaGS.deltaRa9))
  }

  private def updateGameState(newPromptKey: String = promptKey, newAmanda: Amanda = amanda, newRa9: Ra9 = ra9): GameState =
    GameState(newPromptKey, newAmanda, newRa9)

  def updatePromptKey(newPromptKey: String): GameState = updateGameState(newPromptKey = newPromptKey)
  private def updateAmanda(newAmanda: Amanda): GameState = updateGameState(newAmanda = newAmanda)
  private def updateRa9(newRa9: Ra9): GameState = updateGameState(newRa9 = newRa9)

  val prompt: Prompt = keywords2prompts(promptKey)
  val meters = s"Amanda: ${amanda.meter}%,   Software instability: ${ra9.softwareInstability}%"
  val width = if (meters.length < 50) 50 else meters.length
  val divider = "-" * (width + 5)
  val scrollScreen = "\n" * 40

  def cycle: GameState = prompt.cycle(this)

}

case class DeltaGameState(deltaAmanda: DeltaAmanda, deltaRa9: DeltaRa9)
