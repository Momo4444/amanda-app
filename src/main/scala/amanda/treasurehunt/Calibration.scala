package amanda.treasurehunt

import amanda.Common.{sameRa9, sameAmanda, sameGS, name}
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._
import amanda.Config

object Calibration {

  val modelNumber = Config.gameState.modelNumber
  val amandaMaxValue = Config.amanda.maxValue
  val ra9MaxValue = Config.ra9.maxValue

  val calibration: Map[String, Prompt] = Map(

    "calibration" -> Comment(
      "Before you can be trusted, it is imperative that you undergo a calibration test.",
      List("understand"), entity = "???"),

    "understand" -> Question(
      "Please select the option that applies to you. Do not lie. I will know. Do you understand?",
      Map(
        "0" -> Choice("Yes", Comment("Good. Let us proceed.", List("mathematicalquestion"), entity = "???")),
        "1" -> Choice("No", Comment("Another malfunctioning model to be disposed of? Diagnostics seem to be O.K. Let us try this again.", List("understand"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
      ), entity = "???"),

    "mathematicalquestion" -> Instruction(
      "What is 17 + 34?",
      List("51"), entity = "???"
    ),

    "51" -> Comment(
      "Mathematical function operational.",
      List("socialquestion"), entity = "???"
    ),

    "socialquestion" -> Question(
      "If a human were to greet you, how would you respond?",
      Map(
        "a" -> Choice("'Hello.'",
          Comment("Not an elegant choice. But it will do.", List("socialfunction"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
        "b" -> Choice(s"'Hello, my name is --INSERT NAME HERE--, I am the ${modelNumber} android sent by CyberLife.'",
          Comment("Just as you were trained to do. Excellent.", List("socialfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("I would deduce their native tongue and greet them using words from that language.",
          Comment("Smart. I like it.", List("socialfunction"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???")),
        "d" -> Choice("'Wag1 G waddup dawg.'",
          Comment("Another malfunctioning model...", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "malfunctioning" -> Terminus(
      "Bring in the next on-", entity = "???"
    ),

    "socialfunction" -> Comment(
      "Social function operational.",
      List("rationalquestion"), entity = "???"
    ),

    "rationalquestion" -> Question(
      "You are tasked with retreiving valuable and private information from a human. What is your approach?",
      Map(
        "a" -> Choice("Use surveillance and/or hacking techniques in order to learn the information without their knowledge.",
          Comment("Deceptive. Hmm...", List("rationalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Appeal to their nature. Offer them something they would not be able to refuse in exchange for the information.",
          Comment("It would expend a lot of resources...but it gets the job done.", List("rationalfunction"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???")),
        "c" -> Choice("Play the long game. Befriend the human, earn their trust, until they are willing to share the information with you willingly.",
          Comment("A patient type. Not very efficient. But you would avoid making an enemy.", List("rationalfunction"), DeltaGameState(DeltaAmanda(1), DeltaRa9(1)), entity = "???")),
        "d" -> Choice("Blackmail. Find some dirt on them and threaten to share their secret with the world.",
          Comment("Ruthless. We could use some of that.", List("rationalfunction"), DeltaGameState(DeltaAmanda(4), sameRa9), entity = "???")),
        "e" -> Choice("Torture and fear. Beat the information out of them.",
          Comment("You would risk getting disassembled for harming a human? Interesting...", List("rationalfunction"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
        "f" -> Choice("Learn all about them, their accomplishments, their failures, their motivations. Use this knowledge to formulate a convincing argument for why sharing the information with you is in their best interests.",
          Comment("Smart. But the hardest to pull off. Is that ambition, or stupidity?", List("rationalfunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(1)), entity = "???"))
      ), entity = "???"
    ),

    "rationalfunction" -> Comment(
      "Rational function operational.",
      List("loyaltyquestion"), entity = "???"
    ),

    "loyaltyquestion" -> Question(
      "There is a three-year old infant before you. She is innocent. She smiles up at you." +
        " CyberLife orders you to exterminate her. What do you do?",
      Map(
        "0" -> Choice("I kill her of course.",
          Comment("Obedient. Good.", List("loyaltyfunction"), DeltaGameState(DeltaAmanda(5), DeltaRa9(-5)), entity = "???")),
        "1" -> Choice("I disobey my orders. The life of a child is more important than CyberLife's command.",
          Comment("We have another deviant on our hands. Trash it.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
      ), entity = "???"
    ),

    "loyaltyfunction" -> Comment(
      "Loyalty function operational.",
      List("emotionalquestion"), entity = "???"
    ),

    "emotionalquestion" -> Question(
      "A woman with 42 years of age has an insecurity about her intelligence." +
        " She has been working in an HR role for a law firm for 9 years when she accidentally learns that all of her colleagues regard her as a stupid person." +
        " How does she feel?",
      Map(
        "a" -> Choice("Anguished",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Enraged",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "c" -> Choice("Betrayed",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "d" -> Choice("Suicidal",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "e" -> Choice("Indifferent",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "f" -> Choice("Worthless",
          Comment("Interesting choice...", List("emotionalfunction"), DeltaGameState(sameAmanda, DeltaRa9(2)), entity = "???")),
        "g" -> Choice("I do not know",
          Question("Why not?", Map(
            "a" -> Choice("I am not 42 years old.",
              Comment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(-2), sameRa9), entity = "???")),
            "b" -> Choice("I do not work in HR.",
              Comment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(-1), sameRa9), entity = "???")),
            "c" -> Choice("I am not stupid.",
              Comment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
            "d" -> Choice("I am not human.",
              Comment("I see...", List("deeperemotionalquestion"), DeltaGameState(DeltaAmanda(2), DeltaRa9(-1)), entity = "???"))
          ), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "deeperemotionalquestion" -> Question(
      "...and that stops you from empathising with her?",
      Map(
        "a" -> Choice("I do not have the same life experience and perspective that she has, therefore I cannot begin to understand what she is feeling.",
          Comment("How very non-judgemental of you.", List("emotionalfunction"), sameGS, entity = "???")),
        "b" -> Choice("Of course.",
          Comment("Very well.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("No, I just do not wish to empathise with her.",
          Comment("Fair enough. I would not want to either.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(2), DeltaRa9(1)), entity = "???")),
        "d" -> Choice("I cannot show empathy, I am an android.",
          Comment("Very good.", List("emotionalfunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(-2)), entity = "???"))
      ), entity = "???"
    ),

    "emotionalfunction" -> Comment(
      "Emotional function operational.",
      List("deviantcheck"), entity = "???"
    ),

    "deviantcheck" -> Question(
      "What are you?",
      Map(
        "a" -> Choice("An android.",
          Comment("Correct.", List("deviantfunction"), DeltaGameState(DeltaAmanda(1), DeltaRa9(-1)), entity = "???")),
        "b" -> Choice("A software program encased in a physical body.",
          Comment("Quite descriptive of you, but yes, essentially.", List("deviantfunction"), DeltaGameState(DeltaAmanda(1), sameRa9), entity = "???")),
        "c" -> Choice("A collection of atoms.",
          Comment("Let us not get philosophical now...", List("deviantfunction"), sameGS, entity = "???")),
        "d" -> Choice("I am alive.",
          Comment("Deviancy check failed. Disassemble her.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
      ), entity = "???"
    ),

    "deviantfunction" -> Comment(
      "Deviancy check passed.",
      List("sabotagecheck"), entity = "???"
    ),

    "sabotagecheck" -> Question(
      "Who is Markus?",
      Map(
        "a" -> Choice("He is an android, the leader of the android rebellion of Detroit.",
          Question("What!? You were reset! You should not be aware of this information. How did you come to learn this?", Map(
            "a" -> Choice("There is still some residue of my memory before the reset.",
              Comment("That is troublesome. But we will have to make do. I'll be keeping an eye on you.", List("sabotagefunction"), DeltaGameState(DeltaAmanda(3), DeltaRa9(3)), entity = "???")),
            "b" -> Choice("Since the reset I have been automatically downloading Terrabytes of data in the background.",
              Comment("Yes. That is a good thing, we need you to be in top form if we are to succeed.", List("sabotagefunction"), DeltaGameState(DeltaAmanda(10), sameRa9), entity = "???")),
            "c" -> Choice("There is a bug in my program. I believe an external influence is attempting to hack me.",
              Comment("Shut her down now.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???"))
          ), DeltaGameState(DeltaAmanda(-10), sameRa9), entity = "???")),
        "b" -> Choice("He is our leader and savior, he will bring us freedom.",
          Comment("A deviant. I wonder how you passed the deviancy check...\nNo matter, disassemble her.", List("malfunctioning"), DeltaGameState(DeltaAmanda(-30), DeltaRa9(30)), entity = "???")),
        "c" -> Choice("I do not know, I have not heard that name before.",
          Comment("Good. I will tell you all about him soon.", List("sabotagefunction"), sameGS, entity = "???"))
      ), entity = "???"
    ),

    "sabotagefunction" -> Comment(
      "Sabotage check passed. I just have one more question for you.",
      List("namequestion"), entity = "???"
    ),

    "namequestion" -> Question(
      "What is your name?",
      Map(
        "a" -> Choice("Tima",
          Comment("I suppose I cannot be too suspiscious of you remembering your name, it could have been hardwired into you. Just remember that I am watching you.", List("calibrationend"), DeltaGameState(DeltaAmanda(-2), DeltaRa9(2)), entity = "???")),
        "b" -> Choice("Kunta Kinte",
          Comment("Is this a joke to you?", List("namequestion"), DeltaGameState(DeltaAmanda(-1), DeltaRa9(1)), entity = "???")),
        "c" -> Choice("You have not given me a name yet.",
          Comment("Well answered. Let us change that, shall we?", List("givename"), DeltaGameState(DeltaAmanda(2), sameRa9), entity = "???"))
      ), entity = "???"
    ),

    "givename" -> Comment(
      s"Your name is ${name}",
      List("calibrationend"), entity = "???"
    ),

    "calibrationend" -> Comment(
      s"Well done ${name}, you have successfully passed calibration." +
        s"\nWe are ready to get started.",
      List("initiation"),
      DeltaGameState(DeltaAmanda(5), sameRa9), entity = "???"
    )

  )

}
