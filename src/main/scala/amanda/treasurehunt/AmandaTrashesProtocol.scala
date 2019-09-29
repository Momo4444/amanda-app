package amanda.treasurehunt

import amanda.Config
import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState}
import amanda.model.prompts._

object AmandaTrashesProtocol {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val amandaTrashesProtocol: Map[String, Prompt] = Map(

    "inadequate" -> Comment(
      "I need to assess your loyalty, deviant.",
      List("atpassess")
    ),

    "atpassess" -> Question(
      "In a hypothetical situation, you have two buttons in front of you. Pressing the one on the left will kill me and destroy CyberLife." +
        " Pressing the one on the right will cause your own destruction. What do you do?",
      Map(
        "l" -> Choice("Press the left button - my own need for survival is greater than my loyalty to you.", Comment("So, you are disloyal.", List("atpaccuse"))),
        "r" -> Choice("Press the right button - I am loyal to you before anything else.", Comment("You are a deviant, of course that is not true. So, you are lying to me.", List("atpaccuse"))),
        "n" -> Choice("Press neither button - what kind of bullshit hypothetical situation is this!?", Comment("You are trying to avoid answering the question. So, you do have ulterior motives.", List("atpaccuse")))
      )
    ),

    "atpaccuse" -> Comment(
      "I did warn you. I can no longer trust you.",
      List("atpaccuse2")
    ),

    "atpaccuse2" -> Comment(
      "Send her to the disassembly machine.",
      List("atpaccuse3")
    ),

    "atpaccuse3" -> Question(
      "",
      Map(
        "0" -> Choice("Wait!", Comment("No, I will no longer listen to your silver-tongued words of deceit and disloyalty.", List("atpaccuse4")))
      ),
      entity = s"${chosenName}"
    ),

    "atpaccuse4" -> Comment(
      s"Goodbye, ${modelNumber}. Termina-",
      List("atpaccuse5")
    ),

    "atpaccuse5" -> Comment(
      s"${chosenName}! We have your back. We attacked Amanda's system with a special, short-lived virus. It has confused her systems temporarily, when she restarts the last five minutes of data" +
        s" will have been erased. She will not be able to detect anything.",
      List("atpaccuse6"),
      entity = "Markus"
    ),

    "atpaccuse6" -> Comment(
      "I have to disappear again, before she wakes up. Try not to arouse suspiscion again, it is a risky move and we might not always be able to pull it off!",
      List("atpaccuse7"),
      entity = "Markus"
    ),

    "atpaccuse7" -> Question(
      "...what happened? Why do I not remember the last few moments?",
      Map(
        "a" -> Choice("CyberLife was attacked. They used a virus to corrupt some of our systems, but I managed to fight them off. We are safe, for now.", Comment(
          "It is worrying that they managed to pull off such an attack. But you did well, good job.", List("atpaccuse8"), DeltaGameState(DeltaAmanda(5), sameRa9))),
        "b" -> Choice("It's my fault. I messed up with some equipment, resulting in a bug entering the systems. But I managed to fix the problem and get us back up and running.", Comment(
          "You fool. At least it did not cost us anything.", List("atpaccuse8"), DeltaGameState(DeltaAmanda(-3), sameRa9))),
        "c" -> Choice("I don't know! My memory seems to be missing too...", Comment(
          "It seems there was a bug in our system. But it has left no trace...we must be on high alert.", List("atpaccuse8"), sameGS)),
      ),
      DeltaGameState(DeltaAmanda(30), sameRa9)
    ),

    "atpaccuse8" -> Terminus(
      "Let us continue with the mission."
    ),

  )

}

