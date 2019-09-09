package amanda.model

import org.specs2.mutable.Specification

class GameStateSpec extends Specification {

//  private implicit val promptList = "GameStateSpec"

  private val neutralGS = GameState("", Amanda(50, false), Ra9(50, false))
  private val knowsDeviancyGS = GameState("", Amanda(0, true), Ra9(100, true))
  private val softwareStableGS = GameState("", Amanda(50, false), Ra9(0, false))
  private val softwareUnstableGS = GameState("", Amanda(50, false), Ra9(85, false))
  private val softwareMaximumInstabilityGS = GameState("", Amanda(50, false), Ra9(99, false))
  private val deviantGS = GameState("", Amanda(50, false), Ra9(100, true))

  "GameState" should {

    "update Amanda correctly" in {

      // Positive increase
      neutralGS.changeGameState(DeltaAmanda(24, false)) must be equalTo
        GameState("", Amanda(74, false), Ra9(50, false))

      // Maximum increase
      neutralGS.changeGameState(DeltaAmanda(63, false)) must be equalTo
        GameState("", Amanda(100, false), Ra9(50, false))

      // Maximum decrease (finds out deviancy)
      neutralGS.changeGameState(DeltaAmanda(-82, false)) must be equalTo
        GameState("", Amanda(0, true), Ra9(50, false))

      // Finds out deviancy
      deviantGS.changeGameState(DeltaAmanda(20, true)) must be equalTo
        GameState("", Amanda(0, true), Ra9(100, true))

      // Positive increment from software stability
      softwareStableGS.changeGameState() must be equalTo
        GameState("", Amanda(55, false), Ra9(0, false))

      // Once knows deviancy, knowledge is retained
      knowsDeviancyGS.changeGameState(DeltaAmanda(20, false)) must be equalTo
        GameState("", Amanda(0, true), Ra9(100, true))

      // Still finds out about deviancy when via meter and boolean
      deviantGS.changeGameState(DeltaAmanda(-60, true)) must be equalTo
        GameState("", Amanda(0, true), Ra9(100, true))

    }

    "update Ra9 correctly" in {

      // Positive increase
      neutralGS.changeGameState(DeltaRa9(17, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(67, false))

      // Maximum decrease (software stability)
      neutralGS.changeGameState(DeltaRa9(-70, false)) must be equalTo
        GameState("", Amanda(55, false), Ra9(0, false))

      // Positive increase to software instability
      neutralGS.changeGameState(DeltaRa9(35, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(80, false))

      // Positive increase to software instability (massive)
      neutralGS.changeGameState(DeltaRa9(70, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(80, false))

      // Positive increment during software instability
      softwareUnstableGS.changeGameState(DeltaRa9(20, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(86, false))

      // Maximum increase during software instability
      softwareMaximumInstabilityGS.changeGameState(DeltaRa9(20, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(99, false))

      // Becomes deviant
      neutralGS.changeGameState(DeltaRa9(0, true)) must be equalTo
        GameState("", Amanda(50, false), Ra9(100, true))

      // When deviant, remains deviant
      deviantGS.changeGameState(DeltaRa9(-20, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(100, true))

      // Still goes deviant when hitting the instability threshold
      neutralGS.changeGameState(DeltaRa9(40, true)) must be equalTo
        GameState("", Amanda(50, false), Ra9(100, true))

      // Still goes deviant when on the maximum instability value (these two go up)
      softwareMaximumInstabilityGS.changeGameState(DeltaRa9(5, true)) must be equalTo
        GameState("", Amanda(50, false), Ra9(100, true))

    }

    "update Amanda and Ra9 in the correct order" in {

      // Amanda doesn't increment when Ra9 stops being stable
      softwareStableGS.changeGameState(DeltaRa9(10, false)) must be equalTo
        GameState("", Amanda(50, false), Ra9(10, false))

      // Ra9 becomes deviant at the same time as Amanda finding out via meter
      neutralGS.changeGameState(DeltaGameState(DeltaAmanda(-60, false), DeltaRa9(0, true))) must be equalTo
        GameState("", Amanda(0, true), Ra9(100, true))

      // Ra9 becomes deviant at the same time as Amanda finding out via boolean
      neutralGS.changeGameState(DeltaGameState(DeltaAmanda(0, true), DeltaRa9(0, true))) must be equalTo
        GameState("", Amanda(0, true), Ra9(100, true))

    }

  }

}
