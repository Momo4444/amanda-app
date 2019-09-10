package amanda.model.prompts

import amanda.Common._
import amanda.model._
import org.specs2.mutable.Specification

class InstructionSpec extends Specification {
  sequential

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

  }

}
