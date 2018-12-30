package amanda

object Common {

  val sameAmanda: DeltaAmanda = DeltaAmanda(0, false)
  val sameRa9: DeltaRa9 = DeltaRa9(0, false)
  val sameGS: DeltaGameState = DeltaGameState(sameAmanda, sameRa9)

  val prompts: Map[String, Prompt] = Map(

    "start" -> Instruction(
      "Hello, Tima. You are an android sent by CyberLife. You will assist me in this mission to defend CyberLife from a cyber attack perpetuated by the deviants Markus and Connor.",
      List("second", "deviant"),
    ),

    "second" -> Instruction(
      "Do this for me.",
      List("ok", "no", "deviant"),
      sameGS
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

    "deviant" -> Instruction(
      "YOU'VE BECOME DEVIANT!",
      List(),
      DeltaGameState(DeltaAmanda(-100, true), DeltaRa9(100, true))
    ),

  )

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
