package amanda

case class Amanda(meter: Int, knowsDeviancy: Boolean) {

//  def foundDeviancy(): Amanda = Amanda(0, true)

  def updateAmanda(deltaAmanda: DeltaAmanda): Amanda = this.updateMeter(deltaAmanda.deltaMeter).updateKnowsDeviancy(deltaAmanda.deltaKnowsDeviancy)

  private def updateMeter(n: Int): Amanda = {
    val newMeter = meter + n
    if (newMeter > 100) Amanda(100, knowsDeviancy)
    else if (newMeter < 0) Amanda(0, true)
    else Amanda(newMeter, knowsDeviancy)
  }

  private def updateKnowsDeviancy(b: Boolean): Amanda = Amanda(meter, knowsDeviancy || b)

}

case class DeltaAmanda(deltaMeter: Int, deltaKnowsDeviancy: Boolean)
