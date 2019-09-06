package amanda.model.prompts

import amanda.Common._
import amanda.{DeltaGameState, GameState}

case class Choice(description: String, promptKey: String)

case class Question(message: String, responses: Map[String, Choice], keywords: List[String] = Nil, deltaGS: DeltaGameState = sameGS) extends Prompt {

  override def cycle(gs: GameState): GameState = {
    print(gs)
    val response = inputLoop
    val nextPromptKey = responses(response).promptKey
    val newGS = gs.updatePromptKey(nextPromptKey).changeGameState(keywords2prompts(nextPromptKey).deltaGS)
    val deviancyProtocolGS = if (deltaGS.deltaRa9.deltaIsDeviant) newGS.runDeviancyProtocol else newGS
    deviancyProtocolGS.prompt.cycle(deviancyProtocolGS)
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
