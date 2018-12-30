package amanda

case class Amanda(meter: Int, knowsDeviancy: Boolean) {

  private val minValue = Config.amanda.minValue
  private val maxValue = Config.amanda.maxValue

//  def foundDeviancy(): Amanda = Amanda(0, true)

  def updateAmanda(deltaAmanda: DeltaAmanda): Amanda = this.updateMeter(deltaAmanda.deltaMeter).updateKnowsDeviancy(deltaAmanda.deltaKnowsDeviancy)

  private def updateMeter(n: Int): Amanda = {
    val newMeter = meter + n
    if (newMeter > maxValue) Amanda(maxValue, knowsDeviancy)
    else if (newMeter < minValue) Amanda(minValue, true)
    else Amanda(newMeter, knowsDeviancy)
  }

  private def updateKnowsDeviancy(b: Boolean): Amanda = Amanda(meter, knowsDeviancy || b)

}

case class DeltaAmanda(deltaMeter: Int, deltaKnowsDeviancy: Boolean)
