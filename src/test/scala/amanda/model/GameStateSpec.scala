package amanda.model

import org.specs2.mutable.Specification
import amanda.Config

class GameStateSpec extends Specification {

  private val amandaStartValue = Config.amanda.startValue
  private val amandaMaxValue = Config.amanda.maxValue
  private val amandaMinValue = Config.amanda.minValue
  private val amandaSoftwareStabilityIncrement = Config.amanda.softwareStabilityIncrement
  private val ra9SoftwareStabilityValue = Config.ra9.softwareStabilityValue

//  private implicit val promptList = "GameStateSpec"
  private val neutralGS = GameState("", Amanda(amandaStartValue, false), Ra9(50, false))
  private val softwareStableGS = GameState("", Amanda(50, false), Ra9(ra9SoftwareStabilityValue, false))

  "GameState" should {

    "update Amanda correctly" in {

      // Positive increase
      neutralGS.changeGameState(DeltaAmanda(24, false)) must be equalTo
        GameState("", Amanda(amandaStartValue + 24, false), Ra9(50, false))

      // Maximum increase
      neutralGS.changeGameState(DeltaAmanda(63, false)) must be equalTo
        GameState("", Amanda(amandaMaxValue, false), Ra9(50, false))

      // Maximum decrease (finds out deviancy)
      neutralGS.changeGameState(DeltaAmanda(-82, false)) must be equalTo
        GameState("", Amanda(amandaMinValue, true), Ra9(50, false))

      // Finds out deviancy
      neutralGS.changeGameState(DeltaAmanda(20, true)) must be equalTo
        GameState("", Amanda(0, true), Ra9(50, false))

      // Positive increment from software stability
      softwareStableGS.changeGameState() must be equalTo
        GameState("", Amanda(amandaStartValue + amandaSoftwareStabilityIncrement, false), Ra9(ra9SoftwareStabilityValue, false))

    }

  }

}
