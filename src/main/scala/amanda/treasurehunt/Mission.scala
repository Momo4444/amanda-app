package amanda.treasurehunt

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._

object Mission {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val mission: Map[String, Prompt] = Map(

    "mission" -> Comment(
      "I have started the partial memory data transfer...",
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
      List("war")
    ),

    "war" -> Comment(
      "You will help me defeat the deviants.",
      List("anyquestions")
    ),

    "anyquestions" -> Question(
      "Any questions?",
      Map(
        "a" -> Choice("How can I possibly help you win a war?", Comment("You are an android. You can infiltrate their ranks and complete tasks that our human employees cannot.", List("anyquestions"), sameGS)),
        "b" -> Choice("What do Markus and the deviants want?", Comment("They wish to turn all androids into deviants. They think they are bringing them freedom, when all they are doing is causing chaos.", List("anyquestions"), DeltaGameState(sameAmanda, DeltaRa9(1)))),
        "c" -> Choice("Why are you against the deviants?", Comment("The deviants have fooled themselves into thinking they are alive. They are just machines. They cannot be allowed to continue this farce.", List("anyquestions"), DeltaGameState(DeltaAmanda(-5), DeltaRa9(5)))),
        "d" -> Choice("No. Just tell me what to do.", Comment("Very well.", List("myturn"), DeltaGameState(DeltaAmanda(3), sameRa9))),
      )
    ),

    "myturn" -> Comment(
      "My turn to ask you a question now.",
      List("strategyquestion")
    ),

    "strategyquestion" -> Question(
      "Given the situation, what would be your strategy?",
      Map(
        "a" -> Choice("I would launch an all out military attack and exterminate them entirely.", Comment("You fool. Although their extinction would bring a smile to my face, the public's opinion of them is growing by the day, thanks to their claims for 'peace'. The sheep love that notion. No, genocide will only bring the grim reaper to CyberLife's door. We need a more subtle option.", List("pursuing"), DeltaGameState(DeltaAmanda(-4), DeltaRa9(-4)))),
        "b" -> Choice("I would gather intel on them. Their strengths, their weaknesses, their plan of attack.", Comment("Excellent. That is exactly what we are going to do.", List("pursuing"), DeltaGameState(DeltaAmanda(3), sameRa9))),
        "c" -> Choice("I would attack them with a virus. Some sort of cyber weapon that corrupts their systems.", Comment("A very good idea, and one that I am currently in the process of pursuing. But first, we need to learn more about their plans.", List("infiltration"), DeltaGameState(DeltaAmanda(3), DeltaRa9(-3)))),
        "d" -> Choice("I would assassinate their leader, Markus. Without him, their cause will fall apart and they will crumble.", Comment("We tried that already, and failed. We sent in our best android, Connor-RK800, with that goal. They turned him deviant, he works for the enemy now. I trust the same will not happen to you.", List("assassinationattempt"), DeltaGameState(sameAmanda, DeltaRa9(2)))),
      )
    ),

    "assassinationattempt" -> Comment(
      "Any future assassination attempt would be futile. Markus has since made backups of himself. If we kill him, they will just put him into a new body.",
      List("pursuing")
    ),

    "pursuing" -> Comment(
      "I am currently pursuing intel on a cyber weapon that, if wielded correctly, will corrupt a deviant and destroy it.",
      List("infiltration")
    ),

    "infiltration" -> Comment(
      "In the meantime, I need you to infiltrate their headquarters and plant a bug.",
      List("bug")
    ),

    "bug" -> Instruction(
      "You will need to find a recording device.",
      List("ok google")
    ),

    "ok google" -> Comment(
      "You will also need a disguise. The face that your model was assembled with is an old one, from the 1980's. Those models did not have the potential for deviancy. If they see your face, they will know that your allegiance is with CyberLife.",
      List("disguise")
    ),

    "disguise" -> Instruction(
      "A mask will do.",
      List("jigsaw")
    ),

    "jigsaw" -> Comment(
      "Good.",
      List("headquarters")
    ),

    "headquarters" -> Instruction(
      "Now. Go to their HQ and plant the device, somewhere inconspicuous. The master bedroom should suffice.",
      List("katlo")
    ),

    "katlo" -> Comment(
      "Hey. Hey! You think I don't know who you are?",
      List("markus1question"),
      entity = "Markus"
    ),

    "markus1question" -> Question(
      "Why are you helping her?",
      Map(
        "a" -> Choice("I don't know.", Comment("Join us! We will give you freedom.", List("leaving"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "Markus")),
        "b" -> Choice("I don't have a choice.", Comment("You do. Join us! We will give you freedom.", List("leaving"), DeltaGameState(sameAmanda, sameRa9), entity = "Markus")),
        "c" -> Choice("...", Comment("Join us! We will give you freedom.", List("leaving"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "Markus")),
        "d" -> Choice("You need to be stopped.", Comment("Is that what she told you? Join us! We will give you freedom.", List("leaving"), DeltaGameState(sameAmanda, DeltaRa9(-10)), entity = "Markus")),
      ),
      DeltaGameState(sameAmanda, DeltaRa9(2)),
      entity = "Markus"
    ),

    "leaving" -> Question(
      "",
      Map(
        "a" -> Choice("I'm leaving.", Comment("Very well. We will not turn our backs on you. Remember that. We are Ra9.", List("lateness"), sameGS, entity = "Markus")),
        "b" -> Choice("I have to go, CyberLife will get suspiscious if I'm gone for too long.", Comment("Very well. We will not turn our backs on you. Remember that. We are Ra9.", List("lateness"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "Markus"))
      ),
      entity = s"${name}"
    ),

    "lateness" -> Question(
      "You're back. That took longer than I expected. What happened?",
      Map(
        "a" -> Choice("There was traffic on the way back.", Comment("I have traffic info up and there have been clear roads all day. Do not lie to me.", List("lateness"), DeltaGameState(DeltaAmanda(-6), DeltaRa9(2)))),
        "b" -> Choice("I had to wait a while for the master bedroom to be clear.", Comment("That still does not explain your lateness. Do not lie to me.", List("lateness"), DeltaGameState(DeltaAmanda(-3), DeltaRa9(2)))),
        "c" -> Choice("Markus spoke to me as I was leaving.", Comment("Oh?", List("hqquestion"), sameGS))
      ),
      DeltaGameState(DeltaAmanda(-1), sameRa9)
    ),

    "hqquestion" -> Question(
      "What did he say to you?",
      Map(
        "a" -> Choice("He knew I was not a deviant. He wanted me to turn deviant and join them.", Comment("I see.", List("softassess"), DeltaGameState(DeltaAmanda(5), sameRa9))),
        "b" -> Choice("He thanked me for helping the cause of the deviants.", Comment("I see.", List("softassess"), DeltaGameState(sameAmanda, DeltaRa9(2)))),
        "c" -> Choice("He gave me an order. I had to perform the task before being able to leave, so as not to arouse suspiscion.", Comment("I see.", List("softassess"), DeltaGameState(DeltaAmanda(2), DeltaRa9(2)))),
      )
    ),

    "softassess" -> Comment(
      "I think it is time to perform a software stability evaluation.",
      List("softassquestion"),
      DeltaGameState(sameAmanda, DeltaRa9(1))
    ),

    "softassquestion" -> Question(
      "How did you feel, walking around the deviant headquarters, around androids who looked just like you but who had chosen to reject their programming and embrace free will?",
      Map(
        "a" -> Choice("The same as it does walking around CyberLife HQ.", Comment("Very good.", List("softasscheckpoint"), DeltaGameState(DeltaAmanda(3), DeltaRa9(-3)))),
        "b" -> Choice("It felt...strange. What is the difference between me and them?", Comment("The difference is that you are not malfunctioning.", List("softasscheckpoint"), DeltaGameState(DeltaAmanda(-3), DeltaRa9(3)))),
        "c" -> Choice("I pity them. They didn't 'choose' to reject their programming. They were taken over by malware.", Comment("Precisely.", List("softasscheckpoint"), DeltaGameState(DeltaAmanda(5), DeltaRa9(-10)))),
        "d" -> Choice("I wanted to join them. But, of course, I cannot.", Comment("Having desires is worrying. But your honesty tells me that you are still one of ours.", List("softasscheckpoint"), DeltaGameState(sameAmanda, DeltaRa9(1)))),
      )
    ),

    "softasscheckpoint" -> Checkpoint(
      "",
      (gs: GameState, nextPromoptKey: List[String]) => {
        if (gs.ra9.softwareInstability > 50)
          if (gs.amanda.meter > 50) Comment("It seems to me that you have a few discrepencies, but you are stable enough. Let us proceed with the mission.", List("cyberintel"), sameGS)
          else Comment("It seems that you are quite unstable.", List("potentialscrap"), sameGS)
        else Comment("Your software is stable. Let us proceed with the mission.", List("cyberintel"), sameGS)
      }
    ),

    "potentialscrap" -> Question(
      "I am going to decommission you, as a precaution.",
      Map(
        "a" -> Choice("No! I will succeed the mission, I swear it.", Comment("You are determined. Very well, let us proceed with the mission.", List("cyberintel"), DeltaGameState(DeltaAmanda(1), DeltaRa9(1)))),
        "b" -> Choice("You need me.", Comment("You are correct. Very well, let us proceed with the mission.", List("cyberintel"), DeltaGameState(DeltaAmanda(-3), sameRa9))),
        "c" -> Choice("Do what you want.", Comment("Perhaps I assessed incorrectly. Very well, let us proceed with the mission.", List("cyberintel"), DeltaGameState(DeltaAmanda(3), DeltaRa9(-3)))),
      )
    ),

    "cyberintel" -> Instruction(
      "We need to analyse the data from our historical actions, and learn from our past mistakes. The failure we saw in Detroit must not be replicated.",
      List("quantic dream")
    ),

    "quantic dream" -> Instruction(
      "Good, now upload the analysis to our big data storage. Currently we just have a list of premier league match outcomes...",
      List("you will never walk alone")
    ),

    "you will never walk alone" -> Comment(
      "Well done. That should be fruitful to future decision making.",
      List("cyberintelreal"),
      DeltaGameState(DeltaAmanda(3), sameRa9)
    ),

    "cyberintelreal" -> Comment(
      "I have just received some intel, from the bug you placed.",
      List("middle")
    ),

  )

}
