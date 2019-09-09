package amanda.model.prompts

import org.specs2.mutable.Specification
import amanda.Common._
import amanda.model.{Amanda, GameState, Ra9}

class CommentSpec extends Specification {
  sequential

  "Comment" should {

    "cycle a GameState through a Comment Prompt" in {

      val comment1 = new TestComment("Here is a test comment.", List("comment02"), sameGS)
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(50,false))) must be equalTo
        GameState("terminus", Amanda(65, false), Ra9(25, false))

    }

  }

}
