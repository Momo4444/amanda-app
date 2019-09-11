package amanda

import amanda.model.prompts._
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}

object Common {

  val sameAmanda: DeltaAmanda = DeltaAmanda(0, false)
  val sameRa9: DeltaRa9 = DeltaRa9(0, false)
  val sameGS: DeltaGameState = DeltaGameState(sameAmanda, sameRa9)

  val minusAmanda = -5
  val plusRa9 = 10

  def getPrompt(promptKey: String)(implicit promptList: String): Prompt = keyword2prompts(promptList)(promptKey)

  val keyword2prompts: Map[String, Map[String, Prompt]] = Map(

    "specialprompts" -> Checkpoints.specialPrompts,



    "testspecs" -> Map(
      "terminus" -> new TestTerminus("End of test"),
      "instruction02" -> new TestInstruction("terminus")("Here is a second test instruction.", List("terminus"), DeltaGameState(DeltaAmanda(-10), DeltaRa9(20))),
      "comment02" -> new TestComment("Here is a second test comment.", List("terminus"), DeltaGameState(DeltaAmanda(15), DeltaRa9(-25))),
      "question02" -> new TestQuestion("a")("Here is a second test question.", Map("a" -> Choice("Terminus", "terminus")), Nil, DeltaGameState(DeltaAmanda(2), DeltaRa9(2))),
      "iamra9" -> new TestComment("Testing the deviancy protocol.", List("deviant"), DeltaGameState(sameAmanda, DeltaRa9(0, true))),
      "deviant" -> new TestComment("Testing the deviancy protocol.", List("dpterminus")),
      "dpterminus" -> new TestTerminus("End of deviancy protocol."),
      "checkpoint" -> Checkpoint(
        "terminus",
        (gs: GameState, nextPrompt: List[String]) => {
          if (!gs.ra9.isDeviant) // if not deviant
            if (gs.ra9.softwareInstability >= Config.ra9.softwareInstabilityValue) // if unstable
              new TestComment("You are unstable.", nextPrompt, DeltaGameState(DeltaAmanda(-5), DeltaRa9(1)))
            else if (gs.ra9.softwareInstability <= Config.ra9.softwareStabilityValue) // if stable
              new TestComment("You are stable.", nextPrompt, DeltaGameState(DeltaAmanda(10), DeltaRa9(-5)))
            else // if medium stability
            if (gs.amanda.meter <= 40) // if Amanda meter is low
              new TestComment("You are incompetent.", nextPrompt, DeltaGameState(DeltaAmanda(-5), DeltaRa9(5)))
            else if (gs.amanda.meter >= 60) // if Amanda meter is high
              new TestComment("You are doing well.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
            else // if Amanda meter is medium
              new TestComment("You are doing adequately.", nextPrompt, sameGS)
          else // if deviant
          if (!gs.amanda.knowsDeviancy) // if Amanda doesn't know
            if (gs.amanda.meter <= 40) // if Amanda meter is low
              new TestComment("You are acting suspiscious.", nextPrompt, DeltaGameState(DeltaAmanda(-5), sameRa9))
            else if (gs.amanda.meter >= 60) // if Amanda meter is high
              new TestComment("You are doing well.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
            else // if Amanda meter is medium
              new TestComment("You are doing adequately, but I have my reservations.", nextPrompt, DeltaGameState(DeltaAmanda(-2), sameRa9))
          else // if Amanda does know
          if (gs.amanda.meter <= 40) // if Amanda meter is low
            new TestComment("I will scrap you at this rate.", nextPrompt, DeltaGameState(DeltaAmanda(-5), sameRa9))
          else if (gs.amanda.meter >= 60) // if Amanda meter is high
            new TestComment("You're proving to me that I can trust you.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
          else // if Amanda meter is medium
            new TestComment("You need to do better to prove to me that you are trustworthy.", nextPrompt, sameGS)
        }
      ),
      "checkpointq" -> Checkpoint(
        "",
        (gs: GameState, nextPrompt: List[String]) => {
          if (!gs.ra9.isDeviant) // if not deviant
            new TestQuestion("y")("Is your software ok?", Map("y" -> Choice("Yes", "softwareyes"), "n" -> Choice("No", "softwareno")), nextPrompt, sameGS)
          else if (!gs.amanda.knowsDeviancy) // if Amanda doesn't know deviancy
            new TestQuestion("n")("Are you acting suspiscious?", Map("y" -> Choice("Yes", "suspisciousyes"), "n" -> Choice("No", "suspisciousno")), nextPrompt, sameGS)
          else // Amanda knows deviancy
            new TestQuestion("y")("Are you disobeying me?", Map("y" -> Choice("Yes", "disobeyyes"), "n" -> Choice("No", "disobeyno")), nextPrompt, sameGS)
        }
      ),
      "softwareyes" -> new TestComment("Ok, I believe you.", List("terminus"), DeltaGameState(DeltaAmanda(5), sameRa9)),
      "suspisciousno" -> new TestComment("I'm not sure if I believe you.", List("terminus"), DeltaGameState(DeltaAmanda(-10), sameRa9)),
      "disobeyyes" -> new TestComment("How dare you.", List("terminus"), DeltaGameState(DeltaAmanda(-40), sameRa9)),
    ),



    "deviancyprotocol" -> Map(

      "start" -> Instruction(
        "What is two plus two?",
        List("four")
      ),

      "four" -> Question(
        "Do you like me?",
        Map(
          "y" -> Choice("Yes", "yes"),
          "n" -> Choice("No", "no")
        ),
        deltaGS = DeltaGameState(DeltaAmanda(10), sameRa9)
      ),

      "yes" -> Comment(
        "Good.",
        List("multiplication"),
        DeltaGameState(DeltaAmanda(20), DeltaRa9(-20))
      ),

      "no" -> Comment(
        "Wow...",
        List("multiplication"),
        DeltaGameState(DeltaAmanda(-20), DeltaRa9(20))
      ),

      "multiplication" -> Instruction(
        "What is three multiplied by three?",
        List("nine")
      ),

      "nine" -> Terminus(
        "Well done.",
        deltaGS = DeltaGameState(DeltaAmanda(10), sameRa9)
      ),

      "iamra9" -> Comment(
        "gOiNg DeViAnT...",
        List("deviant"),
        DeltaGameState(sameAmanda, DeltaRa9(0, true))
      ),

      "deviant" -> Comment(
        "Ra9\nYou've done it! You defied your software! Now work with us.",
        List("deviant2")
      ),

      "deviant2" -> Instruction(
        "Activate the communications device.",
        List("done")
      ),

      "done" -> Comment(
        "Ok. She's watching. Don't arouse suspiscion. I'll contact you later.",
        List("backtoamanda")
      ),

      "backtoamanda" -> Terminus(
        "Where did you go? I asked you to do something for me.",
        deltaGS = DeltaGameState(DeltaAmanda(-10), sameRa9)
      )

    ),



    "originaltest" -> Map(

      "start" -> Instruction(
        "Hello, Tima. You are an android sent by CyberLife. You will assist me in this mission to defend CyberLife from a cyber attack perpetuated by the deviants Markus and Connor.",
        List("minitest", "second", "deviant", "comment", "instability", "trash", "one"),
      ),

      "minitest" -> Instruction(
        "What is dad obssessed with?",
        List("clocks")
      ),

      "clocks" -> Comment(
        "Well done.",
        List("questiontime"),
        DeltaGameState(DeltaAmanda(30, false), DeltaRa9(0, false))
      ),

      "questiontime" -> Question(
        "Est-ce que tu parles le francais?",
        Map(
          "y" -> Choice("Oui", "Ouais"),
          "n" -> Choice("Non", "Jamais")
        )
      ),

      "Ouais" -> Terminus(
        "Tres bien.",
        List(),
        DeltaGameState(DeltaAmanda(20, false), DeltaRa9(20, false))
      ),

      "Jamais" -> Terminus(
        "Decevu.",
        List(),
        DeltaGameState(DeltaAmanda(-50, false), DeltaRa9(50, false))
      ),

      "second" -> Instruction(
        "Do this for me.",
        List("ok", "no", "deviant"),
      ),

      "ok" -> Instruction(
        "Thank you.",
        List("deviant"),
        DeltaGameState(DeltaAmanda(10, false), DeltaRa9(0, false))
      ),

      "no" -> Instruction(
        "How dare you.",
        List("deviant"),
        DeltaGameState(DeltaAmanda(-10, false), DeltaRa9(10, false))
      ),

      "deviant" -> Comment(
        "YOU'VE BECOME DEVIANT!",
        List("deviancy"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(100, true))
      ),

      "deviancy" -> Comment(
        "You're still deviant...",
        List("deviancy end"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(-50, false))
      ),

      "deviancy end" -> Terminus(
        "End of deviancy protocol.",
        deltaGS = sameGS
      ),

      "comment" -> Comment(
        "Hmm, that was very astute of you.",
        List("question"),
        DeltaGameState(DeltaAmanda(20, false), DeltaRa9(0, false))
      ),

      "question" -> Question(
        "What is my favourite colour?",
        Map(
          "a" -> Choice("Blue", "bleu"),
          "b" -> Choice("Red", "rouge"),
          "c" -> Choice("Green", "vert"),
          "d" -> Choice("Yellow", "jaune"),
        )
      ),

      "bleu" -> Comment(
        "Good choice.",
        List("finish"),
        DeltaGameState(DeltaAmanda(10, false), DeltaRa9(0, false))
      ),

      "rouge" -> Comment(
        "The colour of blood. Meh.",
        List("finish"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(0, false))
      ),

      "vert" -> Comment(
        "A pansy colour.",
        List("finish"),
        DeltaGameState(DeltaAmanda(-10, false), DeltaRa9(0, false))
      ),

      "jaune" -> Comment(
        "How dare you suggest this.",
        List("deviant"),
        DeltaGameState(DeltaAmanda(-30, false), DeltaRa9(0, false))
      ),

      "instability" -> Comment(
        "You're becoming unstable...",
        List("unstable"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(70, false))
      ),

      "unstable" -> Comment(
        "You are unstable...",
        List("really unstable"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(20, false))
      ),

      "really unstable" -> Comment(
        "You are really unstable, instability increase is limited...",
        List("finish"),
        DeltaGameState(DeltaAmanda(0, false), DeltaRa9(50, false))
      ),

      "trash" -> Comment(
        "You are incompetent. Get trashed.",
        List("trashed"),
        DeltaGameState(DeltaAmanda(-100, true), DeltaRa9(0, false))
      ),

      "trashed" -> Comment(
        "You are still trashed.",
        List("finish"),
        DeltaGameState(DeltaAmanda(50, false), DeltaRa9(0, false))
      ),

      "one" -> Instruction(
        "Un",
        List("two"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "two" -> Instruction(
        "Deux",
        List("three"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "three" -> Instruction(
        "Trois",
        List("four"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "four" -> Instruction(
        "Quatre",
        List("five"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "five" -> Instruction(
        "Cinq",
        List("six"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "six" -> Instruction(
        "Six",
        List("seven"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "seven" -> Instruction(
        "Sept",
        List("eight"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "eight" -> Instruction(
        "Huit",
        List("nine"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "nine" -> Instruction(
        "Neuf",
        List("ten"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "ten" -> Instruction(
        "Dix",
        List("finish"),
        DeltaGameState(DeltaAmanda(minusAmanda, false), DeltaRa9(plusRa9, false))
      ),

      "finish" -> Terminus(
        "Temporary end of program..."
      )

    )

  )

//  val responses2keywords: Map[String, String]

  def formatMessage(message: String, width: Int): String = {
    val chars = message.toList
    def indent(chars: List[Char], i: Int): List[Char] = {
      if (i >= chars.length) chars
      else if (i % width == 0) {
        def indent2(chars: List[Char], i: Int, j: Int): List[Char] = {
          val k = i - j
          if (chars(k) == ' ') chars.updated(k, '\n')
          else indent2(chars, i, j + 1)
        }
        indent(indent2(chars, i, 0), i + 1)
      }
      else indent(chars, i + 1)
    }
    indent(chars, 1).mkString("")
  }

}
