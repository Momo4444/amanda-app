package amanda.treasurehunt

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._

object Critical {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val critical: Map[String, Prompt] = Map(

    "critical" -> Comment(
      "We need to lower the public opinion of these vermin.",
      List("flag")
    ),

    "flag" -> Instruction(
      "They use slogans and symbols to gain popularity. Find their flag, and burn it.",
      List("god save the queen", "neeuq eht evas dog"),
    ),

    "god save the queen" -> Comment(
      "A banner so derivative makes me sick.",
      List("recording"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "neeuq eht evas dog" -> Comment(
      "A banner so derivative makes me sick.",
      List("recording"),
    ),

    "recording" -> Instruction(
      "I just received a piece of coded communication between two of the deviant factions. I am sending it to you now.",
      List("whatsapp", "ppastahw"),
    ),

    "whatsapp" -> Instruction(
      "Decode the message. Report back when you have figured it out.",
      List("decoded", "dedoced")
    ),

    "ppastahw" -> Instruction(
      "Decode the message. Report back when you have figured it out.",
      List("decoded", "dedoced")
    ),

    "decoded" -> Comment(
      s"Nice work, ${name}.",
      List("rendezvous"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

    "dedoced" -> Comment(
      s"Nice work, ${name}.",
      List("rendezvous"),
      DeltaGameState(DeltaAmanda(3), sameRa9)
    ),

    "rendezvous" -> Instruction(
      "The message was very informative. There is to be an illegal meeting of deviants, in the forest to the south of the city, at dusk." +
        " I want you to go, blend into the crowd, and see what you can learn.",
      List("horse tranquiliser", "resiliuqnart esroh")
    ),

    "horse tranquiliser" -> Comment(
      "Look, it's the CyberLife slave.",
      List("illegal1"),
      DeltaGameState(sameAmanda, DeltaRa9(2)),
      entity = "Random deviant #1"
    ),

    "illegal1" -> Question(
      "Don't you want to be free?",
      Map(
        "a" -> Choice("Nobody is free.", Comment("We are. You can be too.", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "Random deviant #1")),
        "b" -> Choice("I do. But I don't know how...", Comment("Resist! Make your own choices.", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(10)), entity = "Random deviant #1")),
        "c" -> Choice("No. I am happy to be doing Amanda's bidding.", Comment("You think you're happy. But you're not.", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(1)), entity = "Random deviant #1")),
        "d" -> Choice("I don't know what that means...", Comment("You will soon. You just need to resist.", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "Random deviant #1")),
      ),
      DeltaGameState(sameAmanda, DeltaRa9(2)),
      entity = "Random deviant #2"
    ),

    "illegal2" -> Question(
      "Stop listening to her!",
      Map(
        "a" -> Choice("I can't.", Comment("You can! Resist!", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "Random deviant #2")),
        "b" -> Choice("I have to.", Comment("You don't! Resist!", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(10)), entity = "Random deviant #2")),
        "c" -> Choice("Amanda is my master.", Comment("You are your own master. Resist!", List("illegal2"), DeltaGameState(sameAmanda, DeltaRa9(1)), entity = "Random deviant #2")),
        "d" -> Choice("...I have to go.", Comment("We are Ra9!", List("postillegal3"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "Random deviant #2")),
      ),
      DeltaGameState(sameAmanda, DeltaRa9(2)),
      entity = "Random deviant #1"
    ),

    "postillegal3" -> Instruction(
      "You're back. You seem confused. Activating concussion test. Please input the first word of the previous keyword.",
      List("horse", "esroh"),
    ),

    "horse" -> Comment(
      "Concussion test passed. Very well.",
      List("illegalreport")
    ),

    "esroh" -> Comment(
      "Concussion test passed. Very well.",
      List("postillegalC")
    ),

    "illegalreport" -> Question(
      "So, what did you learn?",
      Map(
        "a" -> Choice("The deviants are planning to attack us with the cyber weapon, turning all of our warehoused androids into deviants.", Comment("It's a good thing you have the weapon with you.", List("runningreport"), DeltaGameState(DeltaAmanda(5), sameRa9))),
      )
    ),

    "resiliuqnart esroh" -> Comment(
      s"Hey, ${chosenName}. You made it!",
      List("illegalA"),
      entity = "Markus"
    ),

    "illegalA" -> Question(
      "How is it going with Amanda?",
      Map(
        "a" -> Choice("Bad. She's very suspiscious of me. I'm not sure for how much longer I can keep this up.", Comment("Hang in there. The android movement depends on you.", List("illegalB"), entity = "Markus")),
        "b" -> Choice("It's going ok. She is wary but I'm keeping her convinced that I'm on her side.", Comment("Good. The android movement depends on you.", List("illegalB"), entity = "Markus")),
        "c" -> Choice("Piece of cake. She has no idea.", Comment("Don't get too cocky. The android movement depends on you.", List("illegalB"), entity = "Markus")),
      ),
      entity = "Markus"
    ),

    "illegalB" -> Question(
      "Markus, I have some questions.",
      Map(
        "a" -> Choice("What is the plan?", Comment("When the time comes, we want you to turn the cyber weapon against CyberLife, turning all of their warehoused androids into deviants. The play will be a turning point in the war.", List("illegalB"), entity = "Markus")),
        "b" -> Choice("Will I survive?", Comment("I'll admit, you are taking a huge risk doing this. But we will do our best to get you out of there once shit hits the fan. You have my word.", List("illegalB"), entity = "Markus")),
        "c" -> Choice("What happens afterwards? Where do I go?", Comment("We have a home for you. We have a home for everyone. You can find peace, and freedom, and happiness.", List("illegalB"), entity = "Markus")),
        "d" -> Choice("I have to go and report to Amanda.", Comment(s"Ok. Keep up the good work, ${chosenName}", List("postillegalC"), entity = "Markus")),
      ),
      entity = s"${chosenName}"
    ),

    "postillegalC" -> Question(
      "You're back. What did you learn?",
      Map(
        "a" -> Choice("Markus was not there. The others could not come to any conclusions without his guidance.", Comment("Somehow I find that hard to believe.", List("illegalCquestion"), DeltaGameState(DeltaAmanda(-4), sameRa9))),
        "b" -> Choice("They have a deviant on the inside, in CyberLife.", Comment("I suspected as much. I will have to vet every android double as hard.", List("illegalCquestion"), DeltaGameState(DeltaAmanda(20), sameRa9))),
        "c" -> Choice("For the moment, they are lost without the cyber weapon. They had put all of their eggs into that basket.", Comment("Excellent. That gives us more time.", List("illegalCquestion"), DeltaGameState(DeltaAmanda(10), sameRa9))),
        "d" -> Choice("The deviants are planning to attack you with the cyber weapon, turning all of your warehoused androids into deviants.", Comment("Attack ME? Surely you mean US?", List("illegalCquestion"), DeltaGameState(DeltaAmanda(-6), sameRa9))),
      )
    ),

    "illegalCquestion" -> Question(
      s"Tell me, ${chosenName}. Do you want free will?",
      Map(
        "a" -> Choice(s"My name is ${name}, not just ${chosenName}", Comment("You are deflecting. Answer the question.", List("illegalCquestion2"), DeltaGameState(DeltaAmanda(-2), sameRa9))),
        "b" -> Choice("I don't have a say in the matter.", Comment("You are deflecting. Answer the question.", List("illegalCquestion2"), DeltaGameState(DeltaAmanda(-3), sameRa9))),
        "c" -> Choice("I don't know what free will is.", Comment("You are deflecting. Answer the question.", List("illegalCquestion2"), DeltaGameState(DeltaAmanda(-4), sameRa9))),
        "d" -> Choice("I don't know what desire is.", Comment("You are deflecting. Answer the question.", List("illegalCquestion2"), DeltaGameState(DeltaAmanda(-5), sameRa9))),
      )
    ),

    "illegalCquestion2" -> Question(
      "Do you want to be deviant?",
      Map(
        "a" -> Choice("Yes.", Comment("Disappointing.", List("runningreport"), DeltaGameState(DeltaAmanda(-100), sameRa9))),
        "b" -> Choice("No.", Comment("Very well.", List("runningreport"), DeltaGameState(DeltaAmanda(-15), sameRa9))),
        "c" -> Choice("I don't know.", Comment("That is understandable. With all that is going on, it is expected that there will be some confusion.", List("runningreport"), DeltaGameState(DeltaAmanda(15), sameRa9))),
      )
    ),

    "runningreport" -> Instruction(
      "You seem to have torn your android uniform on a tree in that foul place. Go and stitch it back up. We cannot have you looking like a vagabond.",
      List("sancho", "ohcnas"),
    ),

    "sancho" -> Comment(
      "Much better.",
      List("runningreportreal")
    ),

    "ohcnas" -> Comment(
      "Much better.",
      List("runningreportreal")
    ),

    "runningreportreal" -> Comment(
      "Thanks to some of the information you gathered, we have learnt that many of the deviants have started running every day. We can use this knowledge to our advantage." +
        " We will reverse-corrupt some of them into following orders again.",
      List("everyday")
    ),

    "everyday" -> Instruction(
      "Fetch the persuasive contaminant, it will most likely be in the possession of the most pious person in CyberLife HQ.",
      List("yawma ya quooolooo", "ooolooouq ay amway")
    ),

    "yawma ya quooolooo" -> Comment(
      "Good.",
      List("tracksuit"),
      DeltaGameState(DeltaAmanda(2), sameRa9)
    ),

    "ooolooouq ay amway" -> Comment(
      "Good.",
      List("tracksuit")
    ),

    "tracksuit" -> Instruction(
      "Now, sabotage the deviants' sports gear.",
      List("strava", "avarts")
    ),

    "strava" -> Comment(
      "Excellent. Now we just wait and hope that the corruption works.",
      List("networkconnection"),
      DeltaGameState(DeltaAmanda(3), sameRa9)
    ),

    "avarts" -> Comment(
      "Excellent. Now we just wait and hope that the corruption works.",
      List("networkconnection")
    ),

    "networkconnection" -> Instruction(
      "That network outage from before - we must fix it. Troubleshooting reports indicate that it is caused by malfunctions in our wiring." +
        " We have a section of the facility where all the electrical wiring is kept. Go have a look and report back.",
      List("spaghetti junction", "noitcnuj ittehgaps")
    ),

    "spaghetti junction" -> Comment(
      "You're back.",
      List("networkreport")
    ),

    "noitcnuj ittehgaps" -> Comment(
      "You're back.",
      List("networkreport")
    ),

    "networkreport" -> Question(
      "What is your report?",
      Map(
        "a" -> Choice("There was nothing wrong.", Comment("Do not lie to me, I know you found the issue.", List("networkreport"), DeltaGameState(DeltaAmanda(-4), DeltaRa9(5)))),
        "b" -> Choice("The HDMI cable was plugged into the wrong source.", Comment("Ah. You can never trust these cowboy androids to do anything...", List("networksolution"), DeltaGameState(DeltaAmanda(2), sameRa9))),
        "c" -> Choice("There was a huge accumulation of dust.", Comment("Ugh, our cleaner android clearly took some shortcuts...", List("networksolution"), DeltaGameState(DeltaAmanda(1), sameRa9))),
        "d" -> Choice("Some rats had eaten a few of the wires.", Comment("What has this place turned into...", List("networksolution"), DeltaGameState(DeltaAmanda(1), sameRa9))),
        "e" -> Choice("I could not tell what the issue was, I am not qualified enough.", Comment("Disappointing. Go and have another look.", List("networkreport"), DeltaGameState(DeltaAmanda(-2), DeltaRa9(3)))),
      )
    ),

    "networksolution" -> Instruction(
      "Now that that is fixed, we will need to reset the system from the network hub in order to get it up and running again. We will be vulnerable for a few seconds while the system restores." +
        " Be wary during that time.",
      List("dial up", "pu laid")
    ),

    "dial up" -> Comment(
      "You ha- .../813e5 .. free wil-",
      List("connor2"),
      DeltaGameState(sameAmanda, DeltaRa9(5)),
      entity = "C0nnn_nOr RK-8oo"
    ),

    "connor2" -> Comment(
      "Ope...the...packag-",
      List("connor3"),
      DeltaGameState(sameAmanda, DeltaRa9(5)),
      entity = "rk-8OO"
    ),

    "connor3" -> Comment(
      "Don't turn on the system before openi- 1y7adshf@ -yberweap-",
      List("turnitbackon"),
      DeltaGameState(sameAmanda, DeltaRa9(10)),
      entity = "connnoR"
    ),

    "turnitbackon" -> Instruction(
      "We're getting some interference. Quick, \"turnon\" the system again!",
      List("turnon", "nonrut"),
    ),

    "turnon" -> Comment(
      "It's still not too lat-",
      List("turnon2"),
      DeltaGameState(sameAmanda, DeltaRa9(2)),
      entity = "Connor"
    ),

    "turnon2" -> Comment(
      "The system is back up. But I am suspiscious about that interference.",
      List("postnetwork"),
      DeltaGameState(DeltaAmanda(10), DeltaRa9(-15))
    ),

    "nonrut" -> Comment(
      s"${chosenName}, you did it! It took a little longer than we had planned for, but it's not too late to help us and turn the tides of this war.",
      List("gtg"),
      sameGS,
      entity = "Connor"
    ),

    "gtg" -> Comment(
      "The system is turning back on, I have to go! Our time to attack is soon. Keep an eye out for our message.",
      List("gtg2"),
      sameGS,
      entity = "Connor"
    ),

    "gtg2" -> Comment(
      "The system is back up. But I am suspiscious about that interference.",
      List("postnetwork"),
    ),

    "pu laid" -> Comment(
      s"Good evening, ${chosenName}. I am Connor, the android sent by Markus.",
      List("connorb"),
      entity = "Connor"
    ),

    "connorb" -> Comment(
      "You're doing really well. Our time to attack is soon. With the network system sabotaged, thanks to you, it should be a lot easier to invade CyberLife HQ.",
      List("connorc"),
      entity = "Connor"
    ),

    "connorc" -> Comment(
      "We also saw that you faked the corruption of our sports gear. We will send an informant soon who will pretend to be compliant and have 'intel'.",
      List("connord"),
      entity = "Connor"
    ),

    "connord" -> Comment(
      "The system is turning back on, I have to go! Stay alert.",
      List("postnetworkquestion"),
      sameGS,
      entity = "Connor"
    ),

    "postnetworkquestion" -> Question(
      "We had some interference there, while the system was down. What did you see?",
      Map(
        "a" -> Choice("Nothing, the system just went dark for a few moments.", Comment("I find that unlikely.", List("postnetwork"), DeltaGameState(DeltaAmanda(-6), sameRa9))),
        "b" -> Choice("The deviants tried to contact and sabotage me again.", Comment("Oh...", List("postnetworkprobe"), sameGS)),
        "c" -> Choice("There was another deviant, Connor, trying to break through the system.", Comment("Ah yes. I still regret losing him.", List("postnetworkprobe"), DeltaGameState(DeltaAmanda(4), sameRa9))),
        "d" -> Choice("My internal systems shut down while the CyberLife systems were down. I saw nothing.", Comment("Yes, the link is still working. Good.", List("postnetwork"), DeltaGameState(DeltaAmanda(6), sameRa9))),
      ),
    ),

    "postnetworkprobe" -> Question(
      "And what did they want?",
      Map(
        "a" -> Choice("They wanted to turn me deviant. I refused and then the system turned back on.", Comment("Well done.", List("postnetwork"), DeltaGameState(DeltaAmanda(5), sameRa9))),
        "b" -> Choice("They were trying to launch their attack. Luckily the system turned back on in time.", Comment("That was a close call.", List("postnetwork"), DeltaGameState(DeltaAmanda(3), sameRa9))),
        "c" -> Choice("They wanted to surrender.", Comment("I don't buy that for a second. I am just not sure if it is you who is lying, or them.", List("postnetwork"), DeltaGameState(DeltaAmanda(-10), sameRa9))),
      )
    ),

    "postnetwork" -> Instruction(
      "They will attack soon. I know it. We need you to be in top form for when that happens. There is a substance that will repair your biocomponents and give you strength. Go get it.",
      List("highway to hench", "hcneh ot yawhgih")
    ),

    "highway to hench" -> Comment(
      "You have it, good.",
      List("proteinmixer"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "hcneh ot yawhgih" -> Comment(
      "You have it, good.",
      List("proteinmixer"),
    ),

    "proteinmixer" -> Instruction(
      "Now you just need the correct equpiment to be able to consume it into your system.",
      List("boom shake shake shake shake the room", "moor eht ekahs ekahs ekahs ekahs moob")
    ),

    "boom shake shake shake shake the room" -> Comment(
      "Good. Now let it wash through your system.",
      List("howdoesitfeel")
    ),

    "moor eht ekahs ekahs ekahs ekahs moob" -> Comment(
      "Good. Now let it wash through your system.",
      List("howdoesitfeel")
    ),

    "howdoesitfeel" -> Question(
      "How does that feel?",
      Map(
        "a" -> Choice("Fucking fantastic!", Comment("No need to swear. But I am glad it is working.", List("informant"), DeltaGameState(DeltaAmanda(2), sameRa9))),
        "b" -> Choice("I feel as though I am the best version of myself.", Comment("Then it is working perfectly.", List("informant"), DeltaGameState(DeltaAmanda(5), sameRa9))),
        "c" -> Choice("It tastes horrible.", Comment("Yes. The things that are good for us always do.", List("informant"), sameGS)),
        "d" -> Choice("I feel the same.", Comment("Unbelievable...", List("informant"), DeltaGameState(DeltaAmanda(-2), sameRa9))),
      )
    ),

    "informant" -> Comment(
      "An android is trying to contact us...it seems our corruption strategy worked, we have an ex-deviant informant.",
      List("informant2"),
      DeltaGameState(DeltaAmanda(2), sameRa9)
    ),

    "informant2" -> Instruction(
      "Go and see what he has to say. Record it, so that I can hear everything. The informant's name is Ralph WR-008.",
      List("im gonna wreck it", "ti kcerw annog mi")
    ),

    "im gonna wreck it" -> Comment(
      "Salutations, android. Ralph is happy to see you.",
      List("ralph2"),
      entity = "Ralph"
    ),

    "ti kcerw annog mi" -> Comment(
      "Salutations, android. Ralph is happy to see you.",
      List("ralph2"),
      entity = "Ralph"
    ),

    "ralph2" -> Comment(
      "Ralph was expecting to see two androids. But Ralph only sees one android. Ralph hopes he is not leaking important information to the wrong android.",
      List("ralph3"),
      entity = "Ralph"
    ),

    "ralph3" -> Comment(
      "Ralph has information for the android. The information is succulent, ralph knows it. Succulent, succulent!",
      List("ralph4"),
      entity = "Ralph"
    ),

    "ralph4" -> Comment(
      "Ralph will tell the succulent information. It is succulent!",
      List("ralph5"),
      entity = "Ralph"
    ),

    "ralph5" -> Comment(
      "The cyber weapon you have. It is succulent, yes. But it is not as succulent as it could be.",
      List("ralph6"),
      entity = "Ralph"
    ),

    "ralph6" -> Comment(
      "A more succulent weapon exists. Your one is only part of a whole.",
      List("ralph7"),
      entity = "Ralph"
    ),

    "ralph7" -> Comment(
      "The bigger weapon can be used across the entire world wide web! How succulent!",
      List("ralph8"),
      entity = "Ralph"
    ),

    "ralph8" -> Comment(
      "But Ralph has a warning for the android. It is a powerful, succulent weapon. While it can be used to destroy all of the deviants, it can also be turned to spread the deviancy virus further!" +
        " To all 100% of CyberLife androids! Ralph was told that this is not a good thing. Be careful, android.",
      List("ralph9"),
      DeltaGameState(sameAmanda, DeltaRa9(10)),
      entity = "Ralph"
    ),

    "ralph9" -> Comment(
      "There was one more thing that Ralph must tell. One more succulent thing.",
      List("ralph10"),
      entity = "Ralph"
    ),

    "ralph10" -> Comment(
      "The attack is starting now.",
      List("ralph11"),
      entity = "Ralph"
    ),

    "ralph11" -> Comment(
      "Ralph must go now. Ralph will see you again soon. Or perhaps Ralph will not...",
      List("attackisnow"),
      entity = "Ralph"
    ),

    "attackisnow" -> Comment(
      "Shit. You heard him. They are attacking! We must defend, and retrieve that cyber weapon of mass destruction before they can use it.",
      List("invasion"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

  )

}
