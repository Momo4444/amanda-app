package amanda.model.prompts

import amanda.{DeltaGameState, GameState}

import scala.io.StdIn

trait Prompt {

  val message: String
  val keywords: List[String]
  val deltaGS: DeltaGameState

  def cycle(gameState: GameState): GameState

  def print(gameState: GameState): Unit
  def inputLoop: String

  def readInput: String = StdIn.readLine().toLowerCase
  def checkInput(input: String, keywords: List[String]): Boolean = {
    keywords.contains(input)
  }

}
