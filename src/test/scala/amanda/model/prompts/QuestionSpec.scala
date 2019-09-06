package amanda.model.prompts

import org.specs2.mutable.Specification

class QuestionSpec extends Specification {

  "Question" should {

    "check that an input matches a list of responses" in {
      new TestQuestion("a")("What is my favourite colour?", Map(
        "a" -> Choice("Blue", "blue"),
        "b" -> Choice("Orange", "orange"),
        "c" -> Choice("Purple", "purple")
      )).inputLoop must be equalTo "a"
      new TestQuestion("b")("What is my favourite colour?", Map(
        "a" -> Choice("Blue", "blue"),
        "b" -> Choice("Orange", "orange"),
        "c" -> Choice("Purple", "purple")
      )).inputLoop must be equalTo "b"
      new TestQuestion("c")("What is my favourite colour?", Map(
        "c" -> Choice("Purple", "purple")
      )).inputLoop must be equalTo "c"
    }

  }

}

class TestQuestion(mockedInput: String)(message: String, responses: Map[String, Choice]) extends Question(message, responses) {
  override def readInput: String = mockedInput.toLowerCase()
}
