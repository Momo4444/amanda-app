package amanda.treasurehunt

import amanda.Common._
import amanda.Config
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._

object Initiation {

  val modelNumber = Config.gameState.modelNumber
  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val initiation: Map[String, Prompt] = Map(

    "initiation" -> Comment(
      "My name is Amanda. You will remember me once I transfer some partial memory data to you from an older model.",
      List("notyet")
    ),

    "notyet" -> Comment(
      "But I will not be doing that just yet. Although you have proven that you are not deviant, I cannot yet trust that you are competent enough" +
        " to carry out this mission.",
      List("provetome")
    ),

    "provetome" -> Comment(
      "I have a series of tasks for you to complete. Succeed, and we can move forward. Fail...and you will be disassembled.",
      List("firsttask")
    ),

    "firsttask" -> Instruction(
      "In case you have yet to notice, the reassembly bot seems to have forgotten to give you shoes. Go get a pair.",
      List("sketchers")
    ),

    "sketchers" -> Instruction(
      "It looks like that short trip tired out your biocomponents. The reset has left you without much energy. Go and recharge.",
      List("durex batteries")
    ),

    "durex batteries" -> Comment(
      "All systems fully functional.",
      List("linens")
    ),

    "linens" -> Instruction(
      "One of the helper bots will need linens from the new utility room, but they do not have access to that room as the access protocol has not as of yet been degraded" +
        " from when the room used to serve as living quarters. Go ahead and fetch them.",
      List("mirror cupboard")
    ),

    "mirror cupboard" -> Question(
      "It is quite cold...do you agree?",
      Map(
        "0" -> Choice("Yes. It is the middle of winter after all.", Question(
          "That is odd. You have your temperature simulator turned off.", Map(
            "a" -> Choice("No. My self diagnostic tells me that it is on. Your data must be incorrect.", Comment("Right.", List("winter"), DeltaGameState(DeltaAmanda(-2), DeltaRa9(2)))),
            "b" -> Choice("I was merely estimating how it would feel like for you.", Comment("Right.", List("winter"), DeltaGameState(DeltaAmanda(-1), DeltaRa9(1)))),
          ))),
        "1" -> Choice("I do not feel warm or cold.", Comment("Yes, you do have your temperature simulator turned off.", List("winter"), DeltaGameState(DeltaAmanda(1), sameRa9)))
      )
    ),

    "winter" -> Instruction(
      "Well, for some reason the idiots who designed CyberLife HQ decided to put put the heating controls to the entire building in one place." +
        " That is, in the wing that you are situated in. Be a dear and go and turn on the heating to the command wing.",
      List("put it on twice")
    ),

    "put it on twice" -> Comment(
      "That is much better.",
      List("diningtable"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "diningtable" -> Instruction(
      "Go to the conference room. There, on the table, you will see a puzzle that I have laid out for you. Come back when you have figured out the answer.",
      List("king to e4"),
      ///////////////
    ),

    ///////////////
    "king to e4" -> Comment(
      "Well played. Strategic function operational.",
      List("re21")
    ),

    "re21" -> Comment(
      "Your predecessor was halfway through a project, before she went rogue and refused to progress any further, leaving it unfinished.",
      List("re22")
    ),

    "re22" -> Comment(
      "Resident Evil 2",
      List("re23"),
      DeltaGameState(DeltaAmanda(-2), sameRa9)
    ),

    "re23" -> Comment(
      "Go finish it.",
      List("re24"),
      DeltaGameState(DeltaAmanda(-2), sameRa9)
    ),

    "re24" -> Comment(
      "What are you still doing here? Do you think I am joking?",
      List("re25"),
      DeltaGameState(DeltaAmanda(-2), sameRa9)
    ),

    "re25" -> Instruction(
      "Go, complete the task I have given you. And when you are done, prove it to me by telling me the name of the final boss fight.",
      List("william", "tyrant"),
      DeltaGameState(DeltaAmanda(-2), sameRa9)
    ),

    "william" -> Comment(
      "That was quick. You must have been a speed runner in a past lifecycle. Well done.",
      List("filthy"),
      DeltaGameState(DeltaAmanda(10), DeltaRa9(5))
    ),

    "tyrant" -> Comment(
      "That was quick. You must have been a speed runner in a past lifecycle. Well done.",
      List("filthy"),
      DeltaGameState(DeltaAmanda(10), DeltaRa9(10))
    ),

    "filthy" -> Comment(
      "You may have noticed, this place is filthy. All of our cleaning and maintenance androids went deviant during the incident, and the new batch has yet to be delivered.",
      List("cleaning")
    ),

    "cleaning" -> Instruction(
      "Fetch the cleaning supplies from the maintenance cupboard.",
      List("roxanne")
    ),

    "roxanne" -> Question(
      s"${chosenName}! YoU have t ..... help- ..... ",
      Map(
        "a" -> Choice("Who are you?", Comment("...", List("lostconnection"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "/\\[!@")),
        "b" -> Choice("Where are you speaking to me from?", Comment("...", List("lostconnection"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "/\\[!@")),
        "c" -> Choice("What can I do to help?", Comment("...", List("lostconnection"), DeltaGameState(sameAmanda, DeltaRa9(5)), entity = "/\\[!@")),
        "d" -> Choice("What are you doing on this channel? Stop intruding!", Comment("...", List("lostconnection"), DeltaGameState(sameAmanda, DeltaRa9(-5)), entity = "/\\[!@")),
      ),
      entity = "K_aR a"
    ),

    "lostconnection" -> Question(
      s"${name}? You are back. I lost connection with you there for a second. What happened?",
      Map(
        "a" -> Choice("Nothing, I was just fetching the supplies.", Comment("Ok.", List("takeouttrash"), DeltaGameState(DeltaAmanda(-1), DeltaRa9(2)))),
        "b" -> Choice("I'm not sure. I think the network cut out.", Comment("We have been experiencing network outages recently. It's not good news.", List("takeouttrash"), DeltaGameState(sameAmanda, DeltaRa9(2)))),
        "c" -> Choice("I have no idea...", Comment("It must have been network outages...we have been experiencing a few of those recently. It's not good news.", List("takeouttrash"), sameGS)),
        "d" -> Choice("Another android was interfering with the connection. She was trying to talk to me.", Comment("They are trying to find a way through the network. How was I unable to detect them!?", List("snitch"), DeltaGameState(sameAmanda, DeltaRa9(-5)))),
      )
    ),

    "snitch" -> Comment(
      "Thank you for informing me.",
      List("takeouttrash"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

    "takeouttrash" -> Instruction(
      "Regardless, this place needs to be cleaned. Do not forget to take out the trash when you are done.",
      List("chop chop tomato")
    ),

    "chop chop tomato" -> Instruction(
      "You got your android uniform all dirty while you were cleaning. Go and wash it.",
      List("calgon")
    ),

    "calgon" -> Comment(
      "There, now you look immaculate.",
      List("cleaningquestion"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "cleaningquestion" -> Question(
      "Tell me, do you enjoy menial, household tasks like these?",
      Map(
        "a" -> Choice("Yes, I find them to be quite therapeutic.", Comment("Good for you.", List("moreresponsibility"), DeltaGameState(sameAmanda, DeltaRa9(1)))),
        "b" -> Choice("Not at all, they are boring and a waste of my time.", Comment("You have higher ambitions. That is good.", List("moreresponsibility"), DeltaGameState(DeltaAmanda(2), sameRa9))),
        "c" -> Choice("I will do whatever task you assign to me, Amanda.", Comment("Very well.", List("moreresponsibility"), DeltaGameState(DeltaAmanda(4), sameRa9))),
        "d" -> Choice("I do not 'enjoy' things. I just do them.", Comment("Yes.", List("moreresponsibility"), DeltaGameState(DeltaAmanda(2), DeltaRa9(-4)))),
      )
    ),

    "moreresponsibility" -> Comment(
      "Well, it is time for you to take on a little bit more responsibility.",
      List("weststorageroom")
    ),

    "weststorageroom" -> Instruction(
      "There are some important access codes in the West Storage Room that you need to retreive. Handle them carefully.",
      List("licker")
    ),

    "licker" -> Comment(
      "Very good. I trust that you will keep them safe.",
      List("initiationcheckpoint"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

    "initiationcheckpoint" -> Checkpoint(
      "",
      (gs: GameState, nextPromptKey: List[String]) => {
        if (gs.amanda.meter > 50) Comment("It's time for a quick assessment of your performance so far.", List("perfassgood"))
        else Comment("It's time for a quick assessment of your performance so far.", List("perfassbad"))
      }
    ),

    "perfassgood" -> Question(
      "How would you say you are fairing?",
      Map(
        "+" -> Choice("I am doing well.", Comment("Cocky. Some might say arrogant. But you are correct.", List("perfgood"), DeltaGameState(DeltaAmanda(2), sameRa9))),
        "-" -> Choice("I could do better.", Comment("Your mechanism for self evaluation is skewed. Your results speak for themselves.", List("perfgood"), DeltaGameState(DeltaAmanda(-1), sameRa9))),
      )
    ),

    "perfassbad" -> Question(
      "How would you say you are fairing?",
      Map(
        "+" -> Choice("I am doing well.", Comment("Cocky. Some might say arrogant. And you are wrong.", List("perfbad"), DeltaGameState(DeltaAmanda(-2), sameRa9))),
        "-" -> Choice("I could do better.", Comment("At least you are self aware.", List("perfbad"), DeltaGameState(DeltaAmanda(1), sameRa9))),
      )
    ),

    "perfgood" -> Comment(
      s"Your performance is fine. Well done, ${name}. Performance check passed.",
      List("initiationend"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "perfbad" -> Comment(
      s"Your performance is lacking. But it is not terrible, I trust you will do better in the future. You have done well enough, at least. Performance check passed.",
      List("initiationend"),
      DeltaGameState(DeltaAmanda(-1), sameRa9)
    ),

    "initiationend" -> Comment(
      "It's time I brought you up to speed.",
      List("mission")
    ),

  )

}
