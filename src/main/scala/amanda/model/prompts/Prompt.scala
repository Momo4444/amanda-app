package amanda.model.prompts

import amanda.Common.getPrompt
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

import scala.io.StdIn

trait Prompt {

  private implicit val promptList = Config.gameState.promptList

  val message: String
  val keywords: List[String]
  val deltaGS: DeltaGameState

  def cycle(gameState: GameState): GameState

  def deviencyProtocol(gs: GameState, oldPromptKey: String): GameState = {
    if (deltaGS.deltaRa9.deltaIsDeviant) {
      val postDeviancyProtocolGS = gs.runDeviancyProtocol // if turning deviant, run the Deviancy Protocol
      getPrompt(oldPromptKey).cycle(postDeviancyProtocolGS) // before returning to the old Prompt
    }
    else gs.prompt.cycle(gs) // otherwise cycle into the next Prompt
  }

  def print(gameState: GameState): Unit
  def inputLoop: String

  def readInput: String = StdIn.readLine().toLowerCase
  def checkInput(input: String, keywords: List[String]): Boolean = {
    keywords.contains(input)
  }

}
