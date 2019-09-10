package amanda.model.prompts

import org.specs2.mutable.Specification

class PromptSpec extends Specification {
  sequential

  "Prompt" should {

    "check an input against a list of keywords" in {
      Prompt.checkInput("blue", List("blue", "orange", "purple")) must be equalTo true
      Prompt.checkInput("yellow", List("blue", "orange", "purple")) must be equalTo false
    }

  }

}
