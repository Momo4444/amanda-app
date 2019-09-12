package amanda.model

import amanda.Common._
import amanda.Config
import amanda.model.prompts.Prompt

case class GameState(promptKey: String, amanda: Amanda, ra9: Ra9, oldPromptKey: String) {

  private val defaultPrintWidth = Config.gameState.defaultPrintWidth
  private val scrollScreenValue = Config.gameState.scrollScreenValue
  private val amandaMinValue = Config.amanda.minValue
  private val amandaMaxValue = Config.amanda.maxValue
  private val amandaDeviancyMultiplier = Config.amanda.amandaDeviancyMultiplier
  private val ra9MaxValue = Config.ra9.maxValue
  private val maximumSoftwareInstabilityValue = Config.ra9.maximumSoftwareInstabilityValue
  private val softwareInstabilityValue = Config.ra9.softwareInstabilityValue
  private val softwareInstabilityIncrement = Config.ra9.softwareInstabilityIncrement
  private val softwareStabilityValue = Config.ra9.softwareStabilityValue
  private val softwareStabilityIncrement = Config.amanda.softwareStabilityIncrement
  private val deviancyProtocolPrompt = Config.ra9.deviancyProtocolPrompt
  private val amandaKnowsProtocolPrompt = Config.amanda.amandaKnowsProtocolPrompt
  private val amandaTrashesProtocolPrompt = Config.amanda.amandaTrashesProtocolPrompt

  private implicit val promptList = Config.gameState.promptList

  def changeGameState(deltaGS: DeltaGameState): GameState = {

    val deltaAmanda = deltaGS.deltaAmanda
    val deltaRa9 = deltaGS.deltaRa9

    val newDeltaAmanda: DeltaAmanda =
      if (deltaAmanda.deltaKnowsDeviancy)
        DeltaAmanda(amandaMaxValue * (-1), true) // if Amanda finds out deviancy, set Amanda meter to min value
      else if (this.ra9.softwareInstability + deltaRa9.deltaSoftwareInstability <= softwareStabilityValue)
        DeltaAmanda(deltaAmanda.deltaMeter + softwareStabilityIncrement, deltaAmanda.deltaKnowsDeviancy) // if software is stable enough, increment Amanda meter
      else if (this.ra9.isDeviant && deltaAmanda.deltaMeter < 0)
        DeltaAmanda((deltaAmanda.deltaMeter * amandaDeviancyMultiplier).toInt, deltaAmanda.deltaKnowsDeviancy) // if deviant, Amanda meter decreases get boosted by multiplier
      else deltaAmanda // otherwise just return the deltaAmanda prescribed by the deltaGS

    val newDeltaRa9: DeltaRa9 =
      if (this.ra9.isDeviant || deltaRa9.deltaIsDeviant)
        DeltaRa9(ra9MaxValue, this.ra9.isDeviant) // if deviant, set software instability to max value
      else {
        if (this.ra9.softwareInstability + deltaRa9.deltaSoftwareInstability >= softwareInstabilityValue) {
          if (this.ra9.softwareInstability >= softwareInstabilityValue)
            if (this.ra9.softwareInstability == maximumSoftwareInstabilityValue && deltaRa9.deltaSoftwareInstability >= 0)
              DeltaRa9(0, deltaRa9.deltaIsDeviant) // if software is at maximum instability, keep it at the maximum
            else if (deltaRa9.deltaSoftwareInstability > 0)
              DeltaRa9(softwareInstabilityIncrement, deltaRa9.deltaIsDeviant) // if software is unstable enough, only increment instability by a bit
            else deltaRa9 // return deltaRa9 if unstable but software instability is decreasing
          else DeltaRa9(softwareInstabilityValue - this.ra9.softwareInstability, deltaRa9.deltaIsDeviant) // if software instability is reaching threshold, set it to the threshold
        }
        else deltaRa9 // otherwise just return the deltaRa9 prescribed by the deltaGS
      }

    this.updateRa9(ra9.updateRa9(newDeltaRa9)).updateAmanda(amanda.updateAmanda(newDeltaAmanda)).updateOldPromptKey(promptKey)
  }

  def changeGameState(deltaAmanda: DeltaAmanda): GameState = changeGameState(DeltaGameState(deltaAmanda, sameRa9))
  def changeGameState(deltaRa9: DeltaRa9): GameState = changeGameState(DeltaGameState(sameAmanda, deltaRa9))
  def changeGameState(): GameState = changeGameState(DeltaGameState(sameAmanda, sameRa9))

  private def updateGameState(newPromptKey: String = promptKey, newAmanda: Amanda = amanda, newRa9: Ra9 = ra9): GameState =
    GameState(newPromptKey, newAmanda, newRa9, oldPromptKey)

  def updatePromptKey(newPromptKey: String): GameState = updateGameState(newPromptKey = newPromptKey)
  private def updateAmanda(newAmanda: Amanda): GameState = updateGameState(newAmanda = newAmanda)
  private def updateRa9(newRa9: Ra9): GameState = updateGameState(newRa9 = newRa9)

  lazy val prompt: Prompt = getPrompt(promptKey)

  def updateOldPromptKey(key: String): GameState = GameState(promptKey, amanda, ra9, key)

  val meters = s"Amanda: ${amanda.meter}%,   Software instability: ${ra9.softwareInstability}%"
  val printWidth = if (meters.length < defaultPrintWidth) defaultPrintWidth else meters.length
  val divider = "-" * (printWidth + 5)
  val scrollScreen = "\n" * scrollScreenValue

  def runDeviancyProtocol: GameState = getPrompt(deviancyProtocolPrompt).cycle(this)

  def runAmandaKnowsProtocol: GameState = getPrompt(amandaKnowsProtocolPrompt).cycle(this)

  def runAmandaTrashesProtocol: GameState = getPrompt(amandaTrashesProtocolPrompt).cycle(this)

}

case class DeltaGameState(deltaAmanda: DeltaAmanda, deltaRa9: DeltaRa9)
