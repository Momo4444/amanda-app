package amanda.model.prompts

import amanda.model.{Amanda, DeltaGameState, GameState, Ra9}
import amanda.Common._

case class SpecialComment(nextPrompt: String, branchSelector: (GameState, List[String]) => Comment) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    branchSelector(gs, List(nextPrompt)).cycle(gs)
  }

  override def print(gs: GameState): Unit = {}
  override def inputLoop: String = ""
  override val message: String = ""
  override val keywords: List[String] = Nil
  override val deltaGS: DeltaGameState = sameGS

}
