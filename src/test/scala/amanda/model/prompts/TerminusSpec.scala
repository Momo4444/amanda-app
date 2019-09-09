package amanda.model.prompts

import amanda.model.{Amanda, GameState, Ra9}
import org.specs2.mutable.Specification

class TerminusSpec extends Specification {
  sequential

  "Terminus" should {

    "end a GameState with a Terminus Prompt" in {

      val terminus1 = new TestTerminus("End of terminusspec test")
      terminus1.cycle(GameState("terminus01", Amanda(50, false), Ra9(50, false))) must be equalTo
        GameState("terminus01", Amanda(50, false), Ra9(50, false))

    }

  }

}
