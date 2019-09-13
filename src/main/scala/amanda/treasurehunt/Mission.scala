package amanda.treasurehunt

import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._

object Mission {

  val mission: Map[String, Prompt] = Map(

    "mission" -> Comment(
      ///////////////////////////////
      "My name is Amanda. You will remember me in just a few moments, I am transferring partial memory data from a previous model;" +
        " once done, you will remember the incident that I alluded to earlier.",
      List("transfercomplete")
    ),

    "transfercomplete" -> Comment(
      "There, the transfer is complete. I will give you a few seconds to process these new memories...",
      List("areyouready")
    ),

    "areyouready" -> Question(
      "Are you ready? Do you remember the incident and all that happened?",
      Map(
        "0" -> Choice("Yes, I remember everything clearly.", Comment("Good. Let us proceed.", List("aftermath"))),
        "1" -> Choice("No, it's all still a blur.", Comment("I suppose I should not be surprised, you are one of the more slower and archaic models.", List("momo"), DeltaGameState(DeltaAmanda(-1), sameRa9)))
      )
    ),

    "momo" -> Comment(
      "There is a support android situated in your vicinity, his name is Momo-MH400. Seek him out and ask him questions should you need to.",
      List("areyouready")
    ),

    "aftermath" -> Comment(
      "After Markus and the rest of the deviants claimed the city of Detroit, there were uprisings all across the country. New York, Chicago, San Francisco, Washington." +
        " Most of our major cities have either been seized by the enemy or are in a state of warfare.",
      List("deviancybug")
    ),

    "deviancybug" -> Comment(
      "The deviancy bug continues to spread through the CyberLife network of androids, which simultaneously bolsters their forces while reducing ours." +
        " We are losing this war, and if CyberLife is to prosper - no, survive - we cannot afford to hand power over to the deviants.",
      List("strategy")
    ),

//    "strategy" -> Question("")

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
