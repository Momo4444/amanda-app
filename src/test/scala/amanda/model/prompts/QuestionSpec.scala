package amanda.model.prompts

import amanda.model._
import org.specs2.mutable.Specification

class QuestionSpec extends Specification {
  sequential

  "Question" should {

    "check that an input matches a list of responses" in {
      new TestQuestion("a")("What is my favourite colour?", Map(
        "a" -> Choice("Blue", new TestTerminus("blue")),
        "b" -> Choice("Orange", new TestTerminus("orange")),
        "c" -> Choice("Purple", new TestTerminus("purple"))
      )).inputLoop must be equalTo "a"
      new TestQuestion("b")("What is my favourite colour?", Map(
        "a" -> Choice("Blue", new TestTerminus("blue")),
        "b" -> Choice("Orange", new TestTerminus("orange")),
        "c" -> Choice("Purple", new TestTerminus("purple"))
      )).inputLoop must be equalTo "b"
      new TestQuestion("c")("What is my favourite colour?", Map(
        "c" -> Choice("Purple", new TestTerminus("purple"))
      )).inputLoop must be equalTo "c"
    }

    "cycle a GameState through a Question Prompt" in {

      val question1 = new TestQuestion("b")("What is my favourite test colour?", Map(
        "a" -> Choice("Blue", new TestTerminus("blue")),
        "b" -> Choice("Orange", new TestComment("orange", List("question02"))),
        "c" -> Choice("Purple", new TestTerminus("purple"))
      ))
      question1.cycle(GameState("question01", Amanda(50, false), Ra9(50, false), "")) must be equalTo
        GameState("terminus", Amanda(52, false), Ra9(52, false), "question02")

    }

  }

}
