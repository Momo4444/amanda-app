package amanda.model

import org.specs2.mutable.Specification
import amanda.Config

class AmandaSpec extends Specification {
  sequential

  private val minValue = Config.amanda.minValue
  private val maxValue = Config.amanda.maxValue

  private val neutralAmanda = Amanda(50, false)
  private val evilAmanda = Amanda(50, true)

  "Amanda" should {

    "update meter correctly" in {
      neutralAmanda.updateAmanda(28) must be equalTo Amanda(78, false)
      neutralAmanda.updateAmanda(-18) must be equalTo Amanda(32, false)
      neutralAmanda.updateAmanda(0) must be equalTo Amanda(50, false)
    }

    "not update meter above max value" in {
      neutralAmanda.updateAmanda(60) must be equalTo Amanda(maxValue, false)
    }

    "not update meter below min value but turn evil" in {
      neutralAmanda.updateAmanda(-60) must be equalTo Amanda(minValue, true)
    }

    "update knowsDeviancy correctly" in {
      neutralAmanda.updateAmanda(false) must be equalTo Amanda(50, false)
      neutralAmanda.updateAmanda(true) must be equalTo Amanda(50, true)
      evilAmanda.updateAmanda(true) must be equalTo Amanda(50, true)
      evilAmanda.updateAmanda(false) must be equalTo Amanda(50, true)
    }

  }

}
