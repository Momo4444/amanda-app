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
      instruction2.deviancyProtocol(GameState("instruction02", Amanda(50, false), Ra9(50, false), "instruction01"), "instruction02") must be equalTo
        GameState("instruction02", Amanda(50, false), Ra9(50, false), "instruction01")

    }

    "trigger the Deviancy Protocol when the Deviancy Prompt is inputted" in {

      Prompt.deviancyProtocolTriggered = false
      val instruction2 = new TestInstruction(deviancyPrompt)("Here is a second test instruction.", List("terminus"), DeltaGameState(DeltaAmanda(-10), DeltaRa9(20)))
      instruction2.cycle(GameState("instruction02", Amanda(50, false), Ra9(50, false), "instruction 01")) must be equalTo
        GameState("terminus", Amanda(50, false), Ra9(100, true), "instruction02")

    }

    "cycle a GameState through the Amanda Knows Protocol" in {
      Prompt.amandaKnowsProtocolTriggered = false
      val instruction1 = new TestInstruction("instruction02")("Amanda will find out your deviancy in the next instruction.", List("instruction02"), sameGS)
      instruction1.cycle(GameState("instruction01", Amanda(5, false), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(30, true), Ra9(100, true), "terminus")
    }

    "trigger the Amanda Knows Protocol when the Amanda meter reaches zero" in {
      Prompt.amandaKnowsProtocolTriggered = false
      val instruction1 = new TestInstruction("instruction02")("Amanda will find out your deviancy in the next instruction.", List("instruction02"), sameGS)
      instruction1.amandaKnowsProtocol(GameState("instruction01", Amanda(0, false), Ra9(100, true), ""), "instruction02") must be equalTo
        GameState("instruction02", Amanda(30, true), Ra9(100, true), "comp2")
    }

    "cycle a GameState through the Amanda Trashes Protocol" in {
      Prompt.amandaKnowsProtocolTriggered = true
      val instruction1 = new TestInstruction("instruction02")("Amanda will trash you in the next instruction.", List("instruction02"), sameGS)
      instruction1.cycle(GameState("instruction01", Amanda(5, true), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(20, true), Ra9(100, true), "terminus")
    }

    "trigger the Amanda Trashes Protocol when the Amanda Knows Protocol gets called a second time" in {
      Prompt.amandaKnowsProtocolTriggered = true
      val instruction1 = new TestInstruction("instruction02")("Amanda will trash you in the next instruction.", List("instruction02"), DeltaGameState(DeltaAmanda(-10), sameRa9))
      instruction1.amandaKnowsProtocol(GameState("instruction01", Amanda(0, true), Ra9(100, true), ""), "instruction02") must be equalTo
        GameState("instruction02", Amanda(20, true), Ra9(100, true), "instruction01")
    }

  }

}
