package amanda.model.prompts

import org.specs2.mutable.Specification
import amanda.Common._
import amanda.model._

class CommentSpec extends Specification {
  sequential

  "Comment" should {

    "cycle a GameState through a Comment Prompt" in {

      val comment1 = new TestComment("Here is a test comment.", List("comment02"), sameGS)
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(50,false), "")) must be equalTo
        GameState("terminus", Amanda(40, false), Ra9(25, false), "comment02")

    }

    "cycle a GameState through the Amanda Knows Protocol" in {
      Prompt.amandaKnowsProtocolTriggered = false
      val comment1 = new TestComment("Amanda will find out your deviancy in the next comment.", List("comment02"), sameGS)
      comment1.cycle(GameState("comment01", Amanda(5, false), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(30, true), Ra9(100, true), "comment02")
    }

    "trigger the Amanda Knows Protocol when the Amanda meter reaches zero" in {
      Prompt.amandaKnowsProtocolTriggered = false
      val comment1 = new TestComment("Amanda will find out your deviancy in the next comment.", List("comment02"), sameGS)
      comment1.amandaKnowsProtocol(GameState("comment01", Amanda(0, false), Ra9(100, true), ""), "comment02") must be equalTo
        GameState("comment02", Amanda(30, true), Ra9(100, true), "comp2")
    }

    "cycle a GameState through the Amanda Trashes Protocol" in {
      Prompt.amandaKnowsProtocolTriggered = true
      val comment1 = new TestComment("Amanda will trash you in the next comment.", List("comment02"), sameGS)
      comment1.cycle(GameState("comment01", Amanda(5, true), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(20, true), Ra9(100, true), "terminus")
    }

    "trigger the Amanda Trashes Protocol when the Amanda Knows Protocol gets called a second time" in {
      Prompt.amandaKnowsProtocolTriggered = true
      val comment1 = new TestComment("Amanda will trash you in the next comment.", List("comment02"), DeltaGameState(DeltaAmanda(-10), sameRa9))
      comment1.amandaKnowsProtocol(GameState("comment01", Amanda(0, true), Ra9(100, true), ""), "comment02") must be equalTo
        GameState("comment02", Amanda(20, true), Ra9(100, true), "comment01")
    }

  }

}
