package amanda.treasurehunt

import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._

object Critical {

  val critical: Map[String, Prompt] = Map(

    "critical" -> Comment(
      "",
      List("fin")
    ),



    "networkconnection" -> Instruction(
      "That network outage from before - we must fix it. Go and have a look at the problem. Report back with what you find.",
      List("dial up")
    ),

    "dial up" -> Question(
      "Your report?",
      Map(
        "a" -> Choice("", Comment("", List(""), DeltaGameState(DeltaAmanda(0), DeltaRa9(0)))),
        "a" -> Choice("", Comment("", List(""), DeltaGameState(DeltaAmanda(0), DeltaRa9(0)))),
      )
    ),

  )

}
