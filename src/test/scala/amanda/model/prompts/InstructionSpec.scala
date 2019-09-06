package amanda.model.prompts

import amanda.DeltaGameState
import amanda.Common._
import org.specs2.mutable.Specification

class InstructionSpec extends Specification {

  "Instruction" should {

    "check that an input matches a list of keywords" in {
      new TestInstruction("blue")("What is my favourite colour?", List("blue", "orange", "purple")).inputLoop must be equalTo "blue"
      new TestInstruction("orange")("What is my favourite colour?", List("blue", "orange", "purple")).inputLoop must be equalTo "orange"
      new TestInstruction("purple")("What is my favourite colour?", List("blue", "orange", "purple")).inputLoop must be equalTo "purple"
    }

  }

}

class TestInstruction(mockedInput: String)(message: String, keywords: List[String], deltaGameState: DeltaGameState = sameGS) extends Instruction(message, keywords, deltaGameState) {
  override def readInput: String = mockedInput.toLowerCase()
}
