package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

case class Choice(description: String, prompt: Prompt)

case class Question(message: String, responses: Map[String, Choice], deltaGS: DeltaGameState = sameGS, entity: String = "Amanda") extends Prompt {

  private implicit val promptList = Config.gameState.promptList

  override val keywords: List[String] = Nil

  override def cycle(gs: GameState): GameState = {
    print(gs)
    val response = inputLoop
    val nextPrompt = responses(response).prompt
    val newGS = gs.changeGameState(nextPrompt.deltaGS)
    nextPrompt.cycle(newGS)
  }

  override def print(gs: GameState): Unit = {
    val formattedMessage = formatMessage(message, gs.printWidth)
    var formattedResponses = ""
    for ((k, v) <- responses) {
      formattedResponses += s"\n${k}) ${v.description}"
    }
    println(
      s"""
         |${gs.scrollScreen}
         |${gs.divider}
         |${entity}:
         |
         |${formattedMessage}
         |${gs.divider}
         |${formattedResponses}
         |${gs.divider}
         |${gs.meters}
         |${gs.divider}
       """.stripMargin
    )
  }

  override def inputLoop: String = {
    val input = readInput
    if (Prompt.checkInput(input, responses.keys.toList)) input
    else {
      println("That is not a valid answer.")
      inputLoop
    }
  }

}

class TestQuestion(mockedInput: String)(message: String, responses: Map[String, Choice], deltaGS: DeltaGameState = sameGS, entity: String = "Amanda")
  extends Question(message, responses, deltaGS, entity) {
  override def readInput: String = mockedInput.toLowerCase()
}
