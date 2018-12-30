package amanda

import scala.io.StdIn
import Common._

case class GameState(promptKey: String, amanda: Amanda, ra9: Ra9) {

  private val defaultPrintWidth = Config.gameState.defaultPrintWidth
  private val scrollScreenValue = Config.gameState.scrollScreenValue
  private val softwareStabilityIncrement = Config.amanda.softwareStabilityIncrement
  private val softwareStabilityValue = Config.ra9.softwareStabilityValue

  def changeGameState(deltaGS: DeltaGameState): GameState = {
    val deltaAmanda = deltaGS.deltaAmanda
    val deltaRa9 = deltaGS.deltaRa9
    val newDeltaAmanda: DeltaAmanda =
      if (this.ra9.softwareInstability + deltaGS.deltaRa9.deltaSoftwareInstability <= softwareStabilityValue)
        DeltaAmanda(deltaAmanda.deltaMeter + softwareStabilityIncrement, deltaAmanda.deltaKnowsDeviancy)
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
  val printWidth = if (meters.length < defaultPrintWidth) defaultPrintWidth else meters.length
  val divider = "-" * (printWidth + 5)
  val scrollScreen = "\n" * scrollScreenValue

  def cycle: GameState = prompt.cycle(this)

}

case class DeltaGameState(deltaAmanda: DeltaAmanda, deltaRa9: DeltaRa9)
