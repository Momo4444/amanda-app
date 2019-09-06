package amanda.model

import org.specs2.mutable.Specification
import amanda.Config

class Ra9Spec extends Specification {

  private val minValue = Config.ra9.minValue
  private val maxValue = Config.ra9.maxValue

  private val neutralRa9 = Ra9(50, false)
  private val deviantRa9 = Ra9(50, true)

  "Ra9" should {

    "update softwareInstability correctly" in {
      neutralRa9.updateRa9(7) must be equalTo Ra9(57, false)
      neutralRa9.updateRa9(-46) must be equalTo Ra9(4, false)
      neutralRa9.updateRa9(0) must be equalTo Ra9(50, false)
    }

    "not update softwareInstability below min value" in {
      neutralRa9.updateRa9(-60) must be equalTo Ra9(minValue, false)
    }

    "not update softwareInstability above max value but turn deviant" in {
      neutralRa9.updateRa9(60) must be equalTo Ra9(maxValue, true)
    }

    "update isDeviant correctly" in {
      neutralRa9.updateRa9(false) must be equalTo Ra9(50, false)
      neutralRa9.updateRa9(true) must be equalTo Ra9(50, true)
      deviantRa9.updateRa9(true) must be equalTo Ra9(50, true)
      deviantRa9.updateRa9(false) must be equalTo Ra9(50, true)
    }

  }

}
