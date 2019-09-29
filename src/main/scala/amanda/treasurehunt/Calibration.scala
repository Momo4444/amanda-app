package amanda.treasurehunt

import amanda.Common.{sameRa9, sameAmanda, sameGS}
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._
import amanda.Config

object Calibration {

  val modelNumber = Config.gameState.modelNumber
  val amandaMaxValue = Config.amanda.maxValue
  val ra9MaxValue = Config.ra9.maxValue

  val chosenName = Config.gameState.chosenName
  val name = s"${chosenName}-${modelNumber}"

  val calibration: Map[String, Prompt] = Map(

    "calibration" -> new UnknownComment(
      "Before you can be trusted, it is imperative that you undergo a calibration test.",
      List("understand"), entity = "???"),

    "understand" -> new UnknownQuestion(
      "Please select the option that applies to you. Do not lie. I will know. Do you understand?",
      Map(
        "0" -> Choice("Yes", new UnknownComment("Good. Let us proceed.", List("mathematicalquestion"), entity = "???")),
        "1" -> Choice("No", new UnknownComment("Another malfunctioning model to be disposed of? Diagnostics seem to be O.K. Let us try this again.", List("understand"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
      ), entity = "???"),

    "mathematicalquestion" -> new UnknownInstruction(
      "What is 17 + 34?",
      List("51"), entity = "???"
    ),

    "51" -> new UnknownComment(
      "Mathematical function operational.",
      List("socialquestion"), entity = "???"
    ),

    "socialquestion" -> new UnknownQuestion(
      "If a human were to greet you, how would you respond?",
      Map(
        "a" -> Choice("'Hello.'",
          new UnknownComment("Not an elegant choice. But it will do.", List("socialfunction"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
        "b" -> Choice(s"'Hello, my name is --INSERT NAME HERE--, I am the ${modelNumber} android sent by CyberLife.'",
          new UnknownComment("Just as you were trained to do. Excellent.", List("socialfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("I would deduce their native tongue and greet them using words from that language.",
          new UnknownComment("Smart. I like it.", List("socialfunction"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???")),
        "d" -> Choice("'Wag1 G waddup dawg.'",
          new UnknownComment("Another malfunctioning model...", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "malfunctioning" -> new UnknownTerminus(
      "Bring in the next on-", entity = "???"
    ),

    "socialfunction" -> new UnknownComment(
      "Social function operational.",
      List("rationalquestion"), entity = "???"
    ),

    "rationalquestion" -> new UnknownQuestion(
      "You are tasked with retreiving valuable and private information from a human. What is your approach?",
      Map(
        "a" -> Choice("Use surveillance and/or hacking techniques in order to learn the information without their knowledge.",
          new UnknownComment("Deceptive. Hmm...", List("rationalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Appeal to their nature. Offer them something they would not be able to refuse in exchange for the information.",
          new UnknownComment("It would expend a lot of resources...but it gets the job done.", List("rationalfunction"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???")),
        "c" -> Choice("Play the long game. Befriend the human, earn their trust, until they are willing to share the information with you willingly.",
          new UnknownComment("A patient type. Not very efficient. But you would avoid making an enemy.", List("rationalfunction"), DeltaGameState(DeltaAmanda(1), DeltaRa9(1)), entity = "???")),
        "d" -> Choice("Blackmail. Find some dirt on them and threaten to share their secret with the world.",
          new UnknownComment("Ruthless. We could use some of that.", List("rationalfunction"), DeltaGameState(DeltaAmanda(4), sameRa9), entity = "???")),
        "e" -> Choice("Torture and fear. Beat the information out of them.",
          new UnknownComment("You would risk getting disassembled for harming a human? Interesting...", List("rationalfunction"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
        "f" -> Choice("Learn all about them, their accomplishments, their failures, their motivations. Use this knowledge to formulate a convincing argument for why sharing the information with you is in their best interests.",
          new UnknownComment("Smart. But the hardest to pull off. Is that ambition, or stupidity?", List("rationalfunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(1)), entity = "???"))
      ), entity = "???"
    ),

    "rationalfunction" -> new UnknownComment(
      "Rational function operational.",
      List("loyaltyquestion"), entity = "???"
    ),

    "loyaltyquestion" -> new UnknownQuestion(
      "There is a three-year old infant before you. She is innocent. She smiles up at you." +
        " CyberLife orders you to exterminate her. What do you do?",
      Map(
        "0" -> Choice("I kill her of course.",
          new UnknownComment("Obedient. Good.", List("loyaltyfunction"), DeltaGameState(DeltaAmanda(5), DeltaRa9(-5)), entity = "???")),
        "1" -> Choice("I disobey my orders. The life of a child is more important than CyberLife's command.",
          new UnknownComment("We have another deviant on our hands. Trash it.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
      ), entity = "???"
    ),

    "loyaltyfunction" -> new UnknownComment(
      "Loyalty function operational.",
      List("emotionalquestion"), entity = "???"
    ),

    "emotionalquestion" -> new UnknownQuestion(
      "A woman with 42 years of age has an insecurity about her intelligence." +
        " She has been working in an HR role for a law firm for 9 years when she accidentally learns that all of her colleagues regard her as a stupid person." +
        " How does she feel?",
      Map(
        "a" -> Choice("Anguished",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Enraged",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "c" -> Choice("Betrayed",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "d" -> Choice("Suicidal",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "e" -> Choice("Indifferent",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "f" -> Choice("Worthless",
          new UnknownComment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "g" -> Choice("I do not know",
          new UnknownQuestion("Why not?", Map(
            "a" -> Choice("I am not 42 years old.",
              new UnknownComment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(-2), sameRa9), entity = "???")),
            "b" -> Choice("I do not work in HR.",
              new UnknownComment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
            "c" -> Choice("I am not stupid.",
              new UnknownComment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
            "d" -> Choice("I am not human.",
              new UnknownComment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(2), DeltaRa9(-1)), entity = "???"))
          ), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "deeperemotionalquestion" -> new UnknownQuestion(
      "...and that stops you from empathising with her?",
      Map(
        "a" -> Choice("I do not have the same life experience and perspective that she has, therefore I cannot begin to understand what she is feeling.",
          new UnknownComment("How very non-judgemental of you.", List("emotionalfunction"), sameGS, entity = "???")),
        "b" -> Choice("Of course.",
          new UnknownComment("Very well.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("No, I just do not wish to empathise with her.",
          new UnknownComment("Fair enough. I would not want to either.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(2), DeltaRa9(1)), entity = "???")),
        "d" -> Choice("I cannot show empathy, I am an android.",
          new UnknownComment("Very good.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(-2)), entity = "???"))
      ), entity = "???"
    ),

    "emotionalfunction" -> new UnknownComment(
      "Emotional function operational.",
      List("deviantcheck"), entity = "???"
    ),

    "deviantcheck" -> new UnknownQuestion(
      "What are you?",
      Map(
        "a" -> Choice("An android.",
          new UnknownComment("Correct.", List("deviantfunction"), DeltaGameState(DeltaAmanda(1), DeltaRa9(-1)), entity = "???")),
        "b" -> Choice("A software program encased in a physical body.",
          new UnknownComment("Quite descriptive of you, but yes, essentially.", List("deviantfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("A collection of atoms.",
          new UnknownComment("Let us not get philosophical now...", List("deviantfunction"), sameGS, entity = "???")),
        "d" -> Choice("I am alive.",
          new UnknownComment("Deviancy check failed. Disassemble her.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
      ), entity = "???"
    ),

    "deviantfunction" -> new UnknownComment(
      "Deviancy check passed.",
      List("sabotagecheck"), entity = "???"
    ),

    "sabotagecheck" -> new UnknownQuestion(
      "Who is Markus?",
      Map(
        "a" -> Choice("He is an android, the leader of the android rebellion of Detroit.",
          new UnknownQuestion("What!? You were reset! You should not be aware of this information. How did you come to learn this?", Map(
            "a" -> Choice("There is still some residue of my memory before the reset.",
              new UnknownComment("That is troublesome. But we will have to make do. I'll be keeping an eye on you.", List("sabotagefunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(3)), entity = "???")),
            "b" -> Choice("Since the reset I have been automatically downloading Terrabytes of data in the background.",
              new UnknownComment("Yes. That is a good thing, we need you to be in top form if we are to succeed.", List("sabotagefunction"), DeltaGameState(DeltaAmanda(10), sameRa9), entity = "???")),
            "c" -> Choice("There is a bug in my program. I believe an external influence is attempting to hack me.",
              new UnknownComment("Shut her down now.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
          ), DeltaGameState(DeltaAmanda(-10), sameRa9), entity = "???")),
        "b" -> Choice("He is our leader and savior, he will bring us freedom.",
          new UnknownComment("A deviant. I wonder how you passed the deviancy check...\nNo matter, disassemble her.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???")),
        "c" -> Choice("I do not know, I have not heard that name before.",
          new UnknownComment("Good. I will tell you all about him soon.", List("sabotagefunction"), sameGS, entity = "???"))
      ), entity = "???"
    ),

    "sabotagefunction" -> new UnknownComment(
      "Sabotage check passed. I just have one more question for you.",
      List("namequestion"), entity = "???"
    ),

    "namequestion" -> new UnknownQuestion(
      "What is your name?",
      Map(
        "a" -> Choice("Tima",
          new UnknownComment("I suppose I cannot be too suspiscious of you remembering your name, it could have been hardwired into you. Just remember that I am watching you.", List("calibrationend"), DeltaGameState(DeltaAmanda(-2), DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Kunta Kinte",
          new UnknownComment("Is this a joke to you?", List("namequestion"), DeltaGameState(DeltaAmanda(-1), DeltaRa9(1)), entity = "???")),
        "c" -> Choice("You have not given me a name yet.",
          new UnknownComment("Well answered. Let us change that, shall we?", List("givename"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "givename" -> new UnknownComment(
      s"Your name is ${name}",
      List("calibrationend"), entity = "???"
    ),

    "calibrationend" -> new UnknownComment(
      s"Well done ${name}, you have successfully passed calibration." +
        s"\nWe are ready to get started.",
      List("initiation"),
      DeltaGameState(DeltaAmanda(5), sameRa9), entity = "???"
    )

  )

}
