package amanda.treasurehunt

import amanda.Config
import amanda.model.prompts.{Comment, Prompt}

object AmandaKnowsProtocol {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val amandaKnowsProtocol: Map[String, Prompt] = Map(

    // Start by amanda asking Tima a question about software assessment, no matter what she picks the conclusion is she is deviant. Then Tima convinces Amanda that she is still on her side.

    "compromised" -> Comment(
      "",
      List("")
    ),

  )

}

