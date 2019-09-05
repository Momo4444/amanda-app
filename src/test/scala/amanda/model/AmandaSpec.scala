package amanda.model

import org.specs2.mutable.Specification
import amanda.Config

class AmandaSpec extends Specification {

  private val minValue = Config.amanda.minValue
  private val maxValue = Config.amanda.maxValue

  "Amanda" should {

    "something" in {

      minValue must be equalTo 0
      maxValue must be equalTo 100

    }

  }

}
