package amanda.model.prompts

import amanda.Common._
import amanda.model._
import amanda.Config
import org.specs2.mutable.Specification

class InstructionSpec extends Specification {
  sequential

  private val deviancyPrompt = Config.ra9.deviancyPrompt

  "Instruction" should {

    "check that an input matches a list of keywords" in {
      new TestInstruction("blue")("What is my favourite colour?", List("blue", "orange", "purple")).inputLoop must be equalTo "blue"
      new TestInstruction("orange")("What is my favourite colour?", List("blue", "orange", "purple")).inputLoop must be equalTo "orange"
      new TestInstruction("purple")("What is my favourite colour?", List("purple")).inputLoop must be equalTo "purple"
    }

    "cycle a GameState through an Instruction Prompt" in {

      val instruction1 = new TestInstruction("instruction02")("Here is a test instruction.", List("instruction02"), sameGS)
      instruction1.cycle(GameState("instruction01", Amanda(50, false), Ra9(50, false), "")) must be equalTo
        GameState("terminus", Amanda(40, false), Ra9(70, false), "instruction02")

    }

    "cycle a GameState through the Deviancy Protocol" in {

      val instruction2 = new TestInstruction(deviancyPrompt)("Here is a second test instruction.", List("terminus"), DeltaGameState(DeltaAmanda(-10), DeltaRa9(20)))
      instruction2.deviencyProtocol(GameState("instruction02", Amanda(50, false), Ra9(50, false), "instruction01"), "instruction02") must be equalTo
        GameState("terminus", Amanda(50, false), Ra9(50, false), "instruction02")

    }

    "trigger the Deviancy Protocol when the Deviancy Prompt is inputted" in {

      val instruction2 = new TestInstruction(deviancyPrompt)("Here is a second test instruction.", List("terminus"), DeltaGameState(DeltaAmanda(-10), DeltaRa9(20)))
      instruction2.cycle(GameState("instruction02", Amanda(50, false), Ra9(50, false), "instruction 01")) must be equalTo
        GameState("terminus", Amanda(50, false), Ra9(100, true), "dpterminus")

    }

  }

}
