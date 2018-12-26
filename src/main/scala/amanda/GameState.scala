package amanda

import scala.io.StdIn
import Common._

case class GameState(promptKey: String, amanda: Amanda, ra9: Ra9) {

  def updateGameState(newPromptKey: String = promptKey, newAmanda: Amanda = amanda, newRa9: Ra9 = ra9): GameState =
    GameState(newPromptKey, newAmanda, newRa9)

  def updatePromptKey(newPromptKey: String): GameState = updateGameState(newPromptKey = newPromptKey)
  def updateAmanda(newAmanda: Amanda): GameState = updateGameState(newAmanda = newAmanda)
  def updateRa9(newRa9: Ra9): GameState = updateGameState(newRa9 = newRa9)

  val prompt: Prompt = prompts(promptKey)
  val meters = s"Amanda: ${amanda.meter}%,   Software instability: ${ra9.softwareInstability}%"
//  val amandaMeter = s"Amanda: ${amanda.meter}%"
//  val softwareInstability = s"Software instability: ${ra9.softwareInstability}%"
  val width = if (meters.length < 50) 50 else meters.length
  val divider = "-" * (width + 5)
  val scrollScreen = "\n" * 40

  def cycle: GameState = prompt.cycle(this)

}
