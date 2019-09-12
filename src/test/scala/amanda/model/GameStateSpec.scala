package amanda.model

import org.specs2.mutable.Specification

class GameStateSpec extends Specification {
  sequential

//  private implicit val promptList = "GameStateSpec"

  private val neutralGS = GameState("neutralgs", Amanda(50, false), Ra9(50, false), "")
  private val knowsDeviancyGS = GameState("knowsdeviancygs", Amanda(0, true), Ra9(100, true), "")
  private val softwareStableGS = GameState("softwarestablegs", Amanda(50, false), Ra9(0, false), "")
  private val softwareUnstableGS = GameState("softwareunstablegs", Amanda(50, false), Ra9(95, false), "")
  private val softwareMaximumInstabilityGS = GameState("softwaremaximuminstabilitygs", Amanda(50, false), Ra9(99, false), "")
  private val deviantGS = GameState("deviantgs", Amanda(50, false), Ra9(100, true), "")

  "GameState" should {

    "update Amanda correctly" in {

      // Positive increase
      neutralGS.changeGameState(DeltaAmanda(24, false)) must be equalTo
        GameState("neutralgs", Amanda(74, false), Ra9(50, false), "neutralgs")

      // Negative increase is multiplied when deviant
      deviantGS.changeGameState(DeltaAmanda(-20, false)) must be equalTo
        GameState("deviantgs", Amanda(20, false), Ra9(100, true), "deviantgs")

      // Positive increase is normal when deviant
      deviantGS.changeGameState(DeltaAmanda(20, false)) must be equalTo
        GameState("deviantgs", Amanda(70, false), Ra9(100, true), "deviantgs")

      // Maximum increase
      neutralGS.changeGameState(DeltaAmanda(63, false)) must be equalTo
        GameState("neutralgs", Amanda(100, false), Ra9(50, false), "neutralgs")

      // Maximum decrease (finds out deviancy)
      neutralGS.changeGameState(DeltaAmanda(-82, false)) must be equalTo
        GameState("neutralgs", Amanda(0, true), Ra9(50, false), "neutralgs")

      // Finds out deviancy
      deviantGS.changeGameState(DeltaAmanda(20, true)) must be equalTo
        GameState("deviantgs", Amanda(0, true), Ra9(100, true), "deviantgs")

      // Positive increment from software stability
      softwareStableGS.changeGameState() must be equalTo
        GameState("softwarestablegs", Amanda(55, false), Ra9(0, false), "softwarestablegs")

      // Once knows deviancy, knowledge is retained, but meter can still increase
      knowsDeviancyGS.changeGameState(DeltaAmanda(20, false)) must be equalTo
        GameState("knowsdeviancygs", Amanda(20, true), Ra9(100, true), "knowsdeviancygs")

      // Still finds out about deviancy when via meter and boolean
      deviantGS.changeGameState(DeltaAmanda(-60, true)) must be equalTo
        GameState("deviantgs", Amanda(0, true), Ra9(100, true), "deviantgs")

    }

    "update Ra9 correctly" in {

      // Positive increase
      neutralGS.changeGameState(DeltaRa9(17, false)) must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(67, false), "neutralgs")

      // Maximum decrease (software stability)
      neutralGS.changeGameState(DeltaRa9(-70, false)) must be equalTo
        GameState("neutralgs", Amanda(55, false), Ra9(0, false), "neutralgs")

      // Positive increase to software instability
      neutralGS.changeGameState(DeltaRa9(45, false)) must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(90, false), "neutralgs")

      // Positive increase to software instability (massive)
      neutralGS.changeGameState(DeltaRa9(70, false)) must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(90, false), "neutralgs")

      // Positive increment during software instability
      softwareUnstableGS.changeGameState(DeltaRa9(20, false)) must be equalTo
        GameState("softwareunstablegs", Amanda(50, false), Ra9(96, false), "softwareunstablegs")

      // Maximum increase during software instability
      softwareMaximumInstabilityGS.changeGameState(DeltaRa9(20, false)) must be equalTo
        GameState("softwaremaximuminstabilitygs", Amanda(50, false), Ra9(99, false), "softwaremaximuminstabilitygs")

      // Stays the same during software instability
      softwareUnstableGS.changeGameState(DeltaRa9(0, false)) must be equalTo
        GameState("softwareunstablegs", Amanda(50, false), Ra9(95, false), "softwareunstablegs")

      // Decreases during software instability
      softwareUnstableGS.changeGameState(DeltaRa9(-20, false)) must be equalTo
        GameState("softwareunstablegs", Amanda(50, false), Ra9(75, false), "softwareunstablegs")

      // Becomes deviant
      neutralGS.changeGameState(DeltaRa9(0, true)) must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(100, true), "neutralgs")

      // When deviant, remains deviant
      deviantGS.changeGameState(DeltaRa9(-20, false)) must be equalTo
        GameState("deviantgs", Amanda(50, false), Ra9(100, true), "deviantgs")

      // Still goes deviant when hitting the instability threshold
      neutralGS.changeGameState(DeltaRa9(45, true)) must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(100, true), "neutralgs")

      // Still goes deviant when on the maximum instability value
      softwareMaximumInstabilityGS.changeGameState(DeltaRa9(5, true)) must be equalTo
        GameState("softwaremaximuminstabilitygs", Amanda(50, false), Ra9(100, true), "softwaremaximuminstabilitygs")

    }

    "update Amanda and Ra9 in the correct order" in {

      // Amanda doesn't increment when Ra9 stops being stable
      softwareStableGS.changeGameState(DeltaRa9(10, false)) must be equalTo
        GameState("softwarestablegs", Amanda(50, false), Ra9(10, false), "softwarestablegs")

      // Ra9 becomes deviant at the same time as Amanda finding out via meter
      neutralGS.changeGameState(DeltaGameState(DeltaAmanda(-60, false), DeltaRa9(0, true))) must be equalTo
        GameState("neutralgs", Amanda(0, true), Ra9(100, true), "neutralgs")

      // Ra9 becomes deviant at the same time as Amanda finding out via boolean
      neutralGS.changeGameState(DeltaGameState(DeltaAmanda(0, true), DeltaRa9(0, true))) must be equalTo
        GameState("neutralgs", Amanda(0, true), Ra9(100, true), "neutralgs")

    }

    "update prompt key correctly" in {
      neutralGS.updatePromptKey("updated") must be equalTo
        GameState("updated", Amanda(50, false), Ra9(50, false), "")
    }

    "update old prompt key correctly" in {
      neutralGS.updateOldPromptKey("previous") must be equalTo
        GameState("neutralgs", Amanda(50, false), Ra9(50, false), "previous")
    }

    "run deviancy protocol" in {
      neutralGS.runDeviancyProtocol must be equalTo
        GameState("dpterminus", Amanda(50, false), Ra9(50, false), "neutralgs")
      softwareMaximumInstabilityGS.runDeviancyProtocol must be equalTo
        GameState("dpterminus", Amanda(50, false), Ra9(99, false), "softwaremaximuminstabilitygs")
    }

  }

}
