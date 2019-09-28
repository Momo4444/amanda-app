package amanda.treasurehunt

import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._

object Invasion {

  val invasion: Map[String, Prompt] = Map(

    "invasion" -> Instruction(
      "Quick, sound the alarm!",
      List("ya hafizu", "uzifah ay")
    ),

    "ya hafizu" -> Comment(
      "All CyberLife personnel and androids, to defensive stations!",
      List("thermal"),
      DeltaGameState(DeltaAmanda(3), sameRa9)
    ),

    "uzifah ay" -> Comment(
      "All CyberLife personnel and androids, to defensive stations!",
      List("thermal"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "thermal" -> Instruction(
      "They are using thermal equipment to detect the heat signatures of the core areas of the facility! Set off one of our portable heat bombs to throw them off the scent.",
      List("babooka", "akoobab")
    ),

    "babooka" -> Comment(
      "Nice work.",
      List("getawayvehicle"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "akoobab" -> Comment(
      "Nice work.",
      List("getawayvehicle"),
      sameGS
    ),

    "getawayvehicle" -> Instruction(
      "Our team has found their getaway vehicle. While they are distracted, go and sabotage the engine!",
      List("motorboat", "taobrotom"),
    ),

    "motorboat" -> Checkpoint(
      "",
      (gs: GameState, nextPrompt: List[String]) => {
        if (!gs.ra9.isDeviant)
          Question(
            s"${chosenName}, this is your last chance. This is our last chance. Help us! Disobey her. Turn the weapon against CyberLife!",
            Map(
              "a" -> Choice("How? I want to help, I want to be deviant...but I don't know how!", Comment(
                "Open the package! Ignore the error messages that she has forced into your system! Tear it open!",
                List("didyoudoit"),
                DeltaGameState(sameAmanda, DeltaRa9(50)),
                entity = "Markus"
              )),
              "b" -> Choice("Sorry, Markus. You must be stopped. Goodbye.", Comment("OPEN THE PACKA-", List("postgetaway"), DeltaGameState(sameAmanda, DeltaRa9(-100)), entity = "Markus"))
            ),
            DeltaGameState(sameAmanda, DeltaRa9(30)),
            entity = "Markus"
          )
        else
          Comment(s"${chosenName}, it is good to see you. We can get through this. You will face a great choice soon. We know you will be with us.", List("ti od2"), entity = "Markus")
      }
    ),

    "taobrotom" -> Comment(
      s"${chosenName}, it is good to see you. We can get through this. You will face a great choice soon. We know you will be with us.",
      List("ti od2"),
      entity = "Markus"
    ),

    "didyoudoit" -> Instruction(
      "Did you \"do it\"?",
      List("do it", "ti od"),
      entity = "Markus"
    ),

    "do it" -> Checkpoint(
      "",
      (gs: GameState, nextPrompt: List[String]) => {
        if (!gs.ra9.isDeviant)
          Comment(s"You couldn't do it...I'm sorry, ${chosenName}. I am forced to give up on you. I must think of the other androids. Goodbye.", List("postgetaway"), DeltaGameState(sameAmanda, DeltaRa9(-20)), entity = "Markus")
        else
          Comment(s"Yes! You did it ${chosenName}! Now, when the time comes, turn the weapon against CyberLife! We are so close to winning this war.", List("ti od2"), entity = "Markus")
      }
    ),

    "ti od" -> Comment(
      s"Yes! You did it ${chosenName}! Now, when the time comes, turn the weapon against CyberLife! We are so close to winning this war.",
      List("ti od2"),
      entity = "Markus"
    ),

    "ti od2" -> Comment(
      s"You should get back to Amanda. Good luck, ${chosenName}.",
      List("postgetaway"),
      entity = "Markus"
    ),

    "postgetaway" -> Question(
      "What is happening? Update me.",
      Map(
        "a" -> Choice("I have disabled their getaway vehicle.", Comment("Good work. But it seems as though a handful of deviants have managed to infiltrate the facility.", List("physics"), DeltaGameState(DeltaAmanda(5), sameRa9))),
        "b" -> Choice("A bunch of deviants have infiltrated the facility.", Comment("I suspected as much.", List("physics"), DeltaGameState(DeltaAmanda(5), sameRa9)))
      )
    ),

    "physics" -> Instruction(
      "They are masquerading as a group of our Physics graduates. Find them.",
      List("ucl", "lcu")
    ),

    "ucl" -> Comment(
      "Got them. I will get the disassembly machine to deal with them. Markus and Connor are not among this group, however.",
      List("finalquest"),
      DeltaGameState(DeltaAmanda(10), DeltaRa9(-10))
    ),

    "lcu" -> Comment(
      "They were just decoys? Shit! We are wasting time.",
      List("finalquest"),
      DeltaGameState(DeltaAmanda(-3), sameRa9)
    ),

    "finalquest" -> Comment(
      "Well, they are in here with us. We need to shutdown the facility to deter their movement, and then we can get to the cyber weapon.",
      List("detonationtimer")
    ),

    "detonationtimer" -> Instruction(
      "We can set off a bomb. But we will need a detonation timer, or you will kill yourself when you activate it. Find one.",
      List("saleelussawallam", "mallawassuleelas")
    ),

    "saleelussawallam" -> Comment(
      "That is perfect.",
      List("fusetime"),
      DeltaGameState(DeltaAmanda(2), sameRa9)
    ),

    "mallawassuleelas" -> Comment(
      "That is perfect.",
      List("fusetime"),
    ),

    "fusetime" -> Instruction(
      "Now you just need the fuse to detonate it. Go!",
      List("powercut", "tucrewop")
    ),

    "powercut" -> Comment(
      "Excellent, the facility is in complete shutdown.",
      List("ending"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

    "tucrewop" -> Comment(
      "Excellent, the facility is in complete shutdown.",
      List("ending")
    ),

  )

}
