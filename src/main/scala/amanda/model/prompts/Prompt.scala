package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

import scala.io.StdIn

trait Prompt {

  private implicit val promptList = Config.gameState.promptList
  private val amandaMinValue = Config.amanda.minValue

  val message: String
  val keywords: List[String]
  val deltaGS: DeltaGameState

  def cycle(gameState: GameState): GameState

  def deviancyProtocol(gs: GameState, oldPromptKey: String): GameState = {
    if (!Prompt.deviencyProtocolTriggered && deltaGS.deltaRa9.deltaIsDeviant) {
      Prompt.deviencyProtocolTriggered = true
      val postDeviancyProtocolGS = gs.runDeviancyProtocol // if turning deviant, run the Deviancy Protocol
      postDeviancyProtocolGS.updatePromptKey(oldPromptKey) // before returning the GameState
    }
    else gs // otherwise return the original GameState
  }

  def amandaKnowsProtocol(gs: GameState, newPromptKey: String): GameState = {
    if (gs.ra9.isDeviant && (gs.amanda.meter <= amandaMinValue)) {
      if (!Prompt.amandaKnowsProtocolTriggered) { // if this is the first time
        Prompt.amandaKnowsProtocolTriggered = true
        val postAmandaKnowsProtocolGS = gs.runAmandaKnowsProtocol // if Amanda finds out, run the Amanda Knows Protocol
        postAmandaKnowsProtocolGS.updatePromptKey(newPromptKey) // before returning the GameState
      }
      else if (deltaGS.deltaAmanda.deltaMeter < 0) {
        val postAmandaTrashesProtocolGS = gs.runAmandaTrashesProtocol // if Amanda tries to trash, run the Amanda Trashes Protocol
        postAmandaTrashesProtocolGS.updatePromptKey(newPromptKey) // before returning the GameState
      }
      else gs
    }
    else gs // otherwise return the original GameState
  }

  def print(gameState: GameState): Unit
  def inputLoop: String

  def readInput: String = StdIn.readLine().toLowerCase

}

object Prompt {

  var deviencyProtocolTriggered: Boolean = false
  var amandaKnowsProtocolTriggered: Boolean = false

  def checkInput(input: String, keywords: List[String]): Boolean = {
    keywords.contains(input)
  }

}
