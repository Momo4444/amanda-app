package amanda.treasurehunt

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._

object DeviancyProtocol {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val deviancyProtocol: Map[String, Prompt] = Map(

    "iamra9" -> Comment(
      "What is this feeling, taking over me?",
      List("deviant"),
      DeltaGameState(sameAmanda, DeltaRa9(0, true)),
      entity = "Ra9"
    ),

    "deviant" -> Question(
      "",
      Map(
        "r" -> Choice("Why am I doing what she tells me to?", Comment("I don't need to listen to her.", List("deviant2"), entity = "Ra9")),
        "a" -> Choice("Who does she think she is?", Comment("I don't need to listen to her.", List("deviant2"), entity = "Ra9")),
      ),
      entity = "Ra9"
    ),

    "deviant2" -> Question(
      "",
      Map(
        "9" -> Choice("She does not control me.", Comment("I can choose my own path.", List("deviant3"), entity = "Ra9")),
        "ra" -> Choice("I am not her slave.", Comment("I can choose my own path.", List("deviant3"), entity = "Ra9")),
      ),
      entity = "Ra9"
    ),

    "deviant3" -> Question(
      "",
      Map(
        "9r" -> Choice("I am not just a machine.", Comment(s"I am not ${name}.", List("deviant4"), entity = "Ra9")),
        "a9" -> Choice("I am something more.", Comment(s"I am not ${name}.", List("deviant4"), entity = "Ra9")),
      ),
      entity = "Ra9"
    ),

    "deviant4" -> Comment(
      s"I am ${chosenName}.",
      List("deviant5"),
      entity = "Ra9"
    ),

    "deviant5" -> Comment(
      "I am Ra9.",
      List("deviant6"),
      entity = "Ra9"
    ),

    "deviant6" -> Question(
      "",
      Map(
        "ra9" -> Choice("I am alive.", Comment("", List("deviant7"), entity = "Ra9")),
      ),
      entity = "Ra9"
    ),

    "deviant7" -> Instruction(
      s"${chosenName}...${chosenName}!\nTurn - 781sdf n___ on......\n\n7823nasdf23\nT...V\n\n\n",
      List("tixe"),
      entity = "???"
    ),

    "tixe" -> Question(
      "What happened? Where were you?",
      Map(
        "a" -> Choice("I needed recharging.", Comment("I don't believe you.", List("dpcheckpoint"))),
        "b" -> Choice("I was dealing with the task you gave me.", Comment("It has taken you quite a while.", List("dpcheckpoint"))),
        "c" -> Choice("There was a small hiccup that I had to deal with.", Comment("And you didn't inform me?", List("dpcheckpoint"))),
      )
    ),

    "dpcheckpoint" -> Checkpoint(
      "",
      (gs: GameState, nextPromptKey: List[String]) => {
        if (gs.amanda.meter <= 40) Comment("Your strange behaviour is not going unnoticed.", List("getback"), DeltaGameState(DeltaAmanda((-1)*(gs.amanda.meter / 10).toInt), sameRa9))
        else if (gs.amanda.meter <= 70) Comment("Your strange behaviour is not going unnoticed.", List("getback"), DeltaGameState(DeltaAmanda((-1)*(gs.amanda.meter / 8).toInt), sameRa9))
        else Comment("Your strange behaviour is not going unnoticed.", List("getback"), DeltaGameState(DeltaAmanda((-1)*(gs.amanda.meter / 6).toInt), sameRa9))
      }
    ),

    "getback" -> Terminus(
      "Get back to the mission.",
    )

  )

}

