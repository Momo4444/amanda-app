package amanda.model.prompts

import amanda.model.{Amanda, GameState, Ra9}
import org.specs2.mutable.Specification

class CheckpointSpec extends Specification {
  sequential

  private val comment1 = new TestComment("Let's test Checkpoints.", List("checkpoint"))

  "Checkpoint" should {

    "choose the correct Prompt for a non-deviant unstable GameState" in {
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(95, false), "")) must be equalTo
        GameState("terminus", Amanda(45, false), Ra9(96, false), "checkpoint")
    }

    "choose the correct Prompt for a non-deviant stable GameState" in {
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(0, false), "")) must be equalTo
        GameState("terminus", Amanda(75, false), Ra9(0, false), "checkpoint")
    }

    "choose the correct Prompt for a non-deviant medium stable low amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(30, false), Ra9(50, false), "")) must be equalTo
        GameState("terminus", Amanda(25, false), Ra9(55, false), "checkpoint")
    }

    "choose the correct Prompt for a non-deviant medium stable high amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(70, false), Ra9(50, false), "")) must be equalTo
        GameState("terminus", Amanda(75, false), Ra9(50, false), "checkpoint")
    }

    "choose the correct Prompt for a non-deviant medium stable medium amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(50, false), "")) must be equalTo
        GameState("terminus", Amanda(50, false), Ra9(50, false), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda doesn't know low amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(30, false), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(23, false), Ra9(100, true), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda doesn't know high amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(70, false), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(75, false), Ra9(100, true), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda doesn't know medium amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(50, false), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(47, false), Ra9(100, true), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda does know low amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(30, true), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(0, true), Ra9(100, true), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda does know high amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(70, true), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(0, true), Ra9(100, true), "checkpoint")
    }

    "choose the correct Prompt for a deviant amanda does know medium amanda GameState" in {
      comment1.cycle(GameState("comment01", Amanda(50, true), Ra9(100, true), "")) must be equalTo
        GameState("terminus", Amanda(0, true), Ra9(100, true), "checkpoint")
    }

  }

}
