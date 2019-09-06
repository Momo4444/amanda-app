package amanda.model

import amanda.Common._
import amanda.Config
import amanda.model.prompts.Prompt

case class GameState(promptKey: String, amanda: Amanda, ra9: Ra9) {

  private val defaultPrintWidth = Config.gameState.defaultPrintWidth
  private val scrollScreenValue = Config.gameState.scrollScreenValue
  private val amandaMinValue = Config.amanda.minValue
  private val ra9MaxValue = Config.ra9.maxValue
  private val softwareInstabilityValue = Config.ra9.softwareInstabilityValue
  private val softwareInstabilityIncrement = Config.ra9.softwareInstabilityIncrement
  private val softwareStabilityValue = Config.ra9.softwareStabilityValue
  private val softwareStabilityIncrement = Config.amanda.softwareStabilityIncrement
  private val deviancyPrompt = Config.ra9.deviancyPrompt

  def changeGameState(deltaGS: DeltaGameState): GameState = {

    val deltaAmanda = deltaGS.deltaAmanda
    val deltaRa9 = deltaGS.deltaRa9

    val newDeltaAmanda: DeltaAmanda =
      if (this.amanda.knowsDeviancy)
        DeltaAmanda(amandaMinValue, this.amanda.knowsDeviancy) // if Amanda knows deviancy, set Amanda meter to min value
      else {
        if (this.ra9.softwareInstability + deltaRa9.deltaSoftwareInstability <= softwareStabilityValue)
          DeltaAmanda(deltaAmanda.deltaMeter + softwareStabilityIncrement, deltaAmanda.deltaKnowsDeviancy) // if software is stable enough, increment Amanda meter
        else deltaAmanda // otherwise just return the deltaAmanda prescribed by the deltaGS
      }

    val newDeltaRa9: DeltaRa9 =
      if (this.ra9.isDeviant)
        DeltaRa9(ra9MaxValue, this.ra9.isDeviant) // if deviant, set software instability to max value
      else {
        if (this.ra9.softwareInstability + deltaRa9.deltaSoftwareInstability >= softwareInstabilityValue) {
          if (this.ra9.softwareInstability >= softwareInstabilityValue)
            DeltaRa9(softwareInstabilityIncrement, deltaRa9.deltaIsDeviant) // if software is unstable enough, only increment instability by a bit
          else DeltaRa9(softwareInstabilityValue - this.ra9.softwareInstability, deltaRa9.deltaIsDeviant) // if software instability is reaching threshold, set it to the threshold
        }
        else deltaRa9 // otherwise just return the deltaRa9 prescribed by the deltaGS
      }

    this.updateRa9(ra9.updateRa9(newDeltaRa9)).updateAmanda(amanda.updateAmanda(newDeltaAmanda))
  }

  def changeGameState(deltaAmanda: DeltaAmanda): GameState = changeGameState(DeltaGameState(deltaAmanda, sameRa9))
  def changeGameState(deltaRa9: DeltaRa9): GameState = changeGameState(DeltaGameState(sameAmanda, deltaRa9))

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

  def runDeviancyProtocol: GameState = keywords2prompts(deviancyPrompt).cycle(this)

}

case class DeltaGameState(deltaAmanda: DeltaAmanda, deltaRa9: DeltaRa9)
