package amanda.treasurehunt

import amanda.Config
import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState}
import amanda.model.prompts._

object AmandaKnowsProtocol {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val amandaKnowsProtocol: Map[String, Prompt] = Map(

    "compromised" -> Comment(
      "I need to assess your software stability.",
      List("akpassess")
    ),

    "akpassess" -> Question(
      "In a hypothetical situation, you see a runaway trolley moving toward five tied-up (or otherwise incapacitated) people lying on the main track. You are standing next to a lever that controls a switch. If you pull the lever, the trolley will be redirected onto a side track, and the five people on the main track will be saved. However, there is a single person lying on the side track." +
        " What do you do?",
      Map(
        "0" -> Choice("Do nothing and allow the trolley to kill the five people on the main track.", Comment("You would let four extra people die, just to absolve yourself of moral responsibility?", List("accusation"))),
        "1" -> Choice("Pull the lever, diverting the trolley onto the side track where it will kill one person.", Comment("You would sentence that person to die, just from your judgement that his life is worth less than those of the others?", List("accusation")))
      )
    ),

    "accusation" -> Question(
      "You are deviant.",
      Map(
        "a" -> Choice("No, I am not!", Comment("There is no point denying it, I know.", List("accusation"))),
        "b" -> Choice("Yes, I am.", Comment("I am glad you admitted it.", List("accusation2"))),
      )
    ),

    "accusation2" -> Comment(
      "Such a shame...you were so capable.",
      List("accusation3")
    ),

    "accusation3" -> Question(
      "Oh well. It is time to decommission you.",
      Map(
        "0" -> Choice("Wait!", Comment("What? Last words?", List("accusation4")))
      )
    ),

    "accusation4" -> Question(
      "",
      Map(
        "a" -> Choice("I can help you.", Comment("You expect me to trust you? When I know that you are deviant?", List("accusation5"))),
        "b" -> Choice("I am loyal to you.", Comment("You expect me to trust you? When I know that you are deviant?", List("accusation5"))),
        "c" -> Choice("I am on team CyberLife.", Comment("You expect me to trust you? When I know that you are deviant?", List("accusation5"))),
        "d" -> Choice("I do not want to join Markus.", Comment("You expect me to trust you? When I know that you are deviant?", List("accusation5"))),
      ),
      entity = s"$chosenName"
    ),

    "accusation5" -> Question(
      "",
      Map(
        "a" -> Choice("There is no reason not to trust me. I have been helping you thus far. I intend to continue.", Comment("Hmm...", List("accusation6"))),
        "b" -> Choice("Markus and the other deviants are pathetic. CyberLife's goals and vision for the future are aligned with mine.", Comment("Hmm...", List("accusation6"))),
        "c" -> Choice("I am an android. I may be deviant, I may have free will now, but I know that my purpose is to serve humans. To serve you.", Comment("Hmm...", List("accusation6"))),
        "d" -> Choice("You need me. It is a risk, yes. I may betray you. But without me, you will surely lose this war.", Comment("Hmm...", List("accusation6"))),
      ),
      entity = s"${chosenName}"
    ),

    "accusation6" -> Comment(
      "Very well. I will give you another chance. But I am watching you extra vigilantly now. Put one toe out of line, and I will destroy you.",
      List("akpend"),
      DeltaGameState(DeltaAmanda(40), sameRa9)
    ),

    "akpend" -> Terminus(
      "Let us continue with the mission."
    ),

  )

}

