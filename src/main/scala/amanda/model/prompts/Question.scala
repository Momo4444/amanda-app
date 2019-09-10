package amanda.model.prompts

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaGameState, GameState}

case class Choice(description: String, promptKey: String)

case class Question(message: String, responses: Map[String, Choice], keywords: List[String] = Nil, deltaGS: DeltaGameState = sameGS) extends Prompt {

  private implicit val promptList = Config.gameState.promptList

  override def cycle(gs: GameState): GameState = {

    print(gs)
    val response = inputLoop
    val nextPromptKey = responses(response).promptKey
    val newGS = gs.changeGameState(getPrompt(nextPromptKey).deltaGS).updatePromptKey(nextPromptKey)

    if (deltaGS.deltaRa9.deltaIsDeviant) {
      val postDeviancyProtocolGS = newGS.runDeviancyProtocol // if turning deviant, run the Deviancy Protocol
      getPrompt(gs.oldPromptKey).cycle(postDeviancyProtocolGS) // before returning to the old Prompt
    }
    else newGS.prompt.cycle(newGS) // otherwise cycle into the next Prompt

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
         |${formattedMessage}
         |${gs.divider}
         |${formattedResponses}
         |${gs.divider}
       """.stripMargin
    )
  }

  override def inputLoop: String = {
    val input = readInput
    if (checkInput(input, responses.keys.toList)) input
    else {
      println("That is not a valid answer, try again.")
      inputLoop
    }
  }

}

class TestQuestion(mockedInput: String)(message: String, responses: Map[String, Choice], keywords: List[String] = Nil, deltaGS: DeltaGameState = sameGS)
  extends Question(message, responses, keywords, deltaGS) {
  override def readInput: String = mockedInput.toLowerCase()
}
