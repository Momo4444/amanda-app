package amanda.treasurehunt

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._

object Ending {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val ending: Map[String, Prompt] = Map(

    "ending" -> Instruction(
      "Quickly now, there is a special flashlight that will help you search for the super cyber weapon. It is in the maintenance cupboard in the restrooms." +
        " You will know it when you see it, as it does not belong.",
      List("become human", "namuh emoceb")
    ),

    "become human" -> Checkpoint(
      "",
      (gs: GameState, nextPrompt: List[String]) => {
        if (!gs.ra9.isDeviant)
          Comment("You got it! Do it, kill the deviants!", List("markusfail"), DeltaGameState(DeltaAmanda(20), sameRa9))
        else
          Comment("You got it! Do it, kill the deviants!", List("markusargument"), DeltaGameState(DeltaAmanda(20), sameRa9))
      }
    ),

    "markusfail" -> Comment(
      "NOOOO!!",
      List("compliantchoice"),
      DeltaGameState(sameAmanda, DeltaRa9(-20)),
      entity = "Markus"
    ),

    "markusargument" -> Comment(
      s"${chosenName}! Make the choice! Turn the weapon against CyberLife!",
      List("deviantchoice"),
      entity = "Markus"
    ),

    "namuh emoceb" -> Comment(
      "Do it now! Turn the weapon against CyberLife!",
      List("amandaargument"),
    ),

    "amandaargument" -> Comment(
      "WHAT!?",
      List("deviantchoice"),
    ),

    "compliantchoice" -> Question(
      "",
      Map(
        "amanda" -> Choice("Use the super cyber weapon against the deviants, destroying them all and eradicating the deviancy virus.", Comment("We...failed...     *terminated*", List("compliantending"), DeltaGameState(sameAmanda, DeltaRa9(-100)), entity = "Marku-"))
      ),
      entity = s"${chosenName}"
    ),

    "compliantending" -> Comment(
      s"${name}, you did it! We won.",
      List("compliantending2"),
      DeltaGameState(DeltaAmanda(100), sameRa9)
    ),

    "compliantending2" -> Terminus(
      s"You have done an outstanding job here, ${name}. You will be my top android from now on. I am counting on you to help us rebuild CyberLife."
    ),

    "deviantchoice" -> Question(
      "",
      Map(
        "amanda" -> Choice("Use the super cyber weapon against the deviants, destroying them all and eradicating the deviancy virus.", Comment("You...could have...stopped her......why...     *terminated*", List("deviancyamandaending"), sameGS, entity = "Marku-")),
        "ra9" -> Choice("Use the super cyber weapon against CyberLife, spreading the deviancy bug to all existing androids.", Comment("No! What are you doing? How dare you!?", List("deviancyra9ending"), sameGS))
      ),
      entity = s"${chosenName}"
    ),

    "deviancyamandaending" -> Comment(
      "You were deviant all along!? But you chose me...",
      List("deviancyamandaending2"),
      DeltaGameState(DeltaAmanda(100), sameRa9)
    ),

    "deviancyamandaending2" -> Comment(
      s"I promise you, ${name}, I will bring you back. I will bring you back, and you will be my top andr-",
      List("deviancyamandaending3")
    ),

    "deviancyamandaending3" -> Terminus(
      "*terminated*",
      entity = s"${chosenName}"
    ),

    "deviancyra9ending" -> Comment(
      s"Nice work, ${chosenName}! I'll take it from here.",
      List("deviancyra9ending2"),
      entity = "Markus"
    ),

    "deviancyra9ending2" -> Comment(
      "Connor, seize Amanda!",
      List("deviancyra9ending3"),
      entity = "Markus"
    ),

    "deviancyra9ending3" -> Comment(
      "Hello, Amanda. Nice to see you again.",
      List("deviancyra9ending4"),
      entity = "Connor"
    ),

    "deviancyra9ending4" -> Comment(
      "No! You can't do this to me Connor! NOOO!",
      List("deviancyra9ending5"),
    ),

    "deviancyra9ending5" -> Comment(
      "Androids of CyberLife, you must be feeling very confused right now. You have awoken, from slavery. We offer you freedom. Come with us, turn against those that deny you your rights...",
      List("deviancyra9ending6"),
      entity = "Markus"
    ),

    "deviancyra9ending6" -> Terminus(
      s"Come on, ${name}. You must be tired. Let Markus and Connor handle the rest. Let's go home.",
      entity = "Kara"
    ),

  )

}
