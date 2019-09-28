package amanda.treasurehunt

import amanda.Config
import amanda.model.prompts.{Comment, Prompt}

object AmandaTrashesProtocol {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val amandaTrashesProtocol: Map[String, Prompt] = Map(

    "inadequate" -> Comment(
      "",
      List("")
    ),

  )

}

