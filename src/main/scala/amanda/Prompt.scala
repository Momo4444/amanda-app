package amanda

import scala.io.StdIn

trait Prompt {

  def cycle(gameState: GameState): GameState

  def print(gameState: GameState): Unit
  def inputLoop(gameState: GameState): String
  def checkInput(input: String, keywords: List[String]): Boolean

  def readInput: String = StdIn.readLine()

}
