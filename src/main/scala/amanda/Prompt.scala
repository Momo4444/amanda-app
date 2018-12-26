package amanda

import scala.io.StdIn

trait Prompt {

  val message: String
  val keywords: List[String]

  def cycle(gameState: GameState): GameState

  def print(gameState: GameState): Unit
  def inputLoop: String
  def checkInput(input: String, keywords: List[String]): Boolean

  def readInput: String = StdIn.readLine().toLowerCase

}
