package amanda.model

import amanda.Config

case class Amanda(meter: Int, knowsDeviancy: Boolean) {

  private val minValue = Config.amanda.minValue
  private val maxValue = Config.amanda.maxValue

//  def foundDeviancy(): Amanda = Amanda(0, true)

  def updateAmanda(deltaAmanda: DeltaAmanda): Amanda = this.updateMeter(deltaAmanda.deltaMeter).updateKnowsDeviancy(deltaAmanda.deltaKnowsDeviancy)

  def updateAmanda(deltaMeter: Int): Amanda = updateAmanda(DeltaAmanda(deltaMeter))
  def updateAmanda(deltaKnowsDeviancy: Boolean): Amanda = updateAmanda(DeltaAmanda(0, deltaKnowsDeviancy))
  def updateAmanda(deltaMeter: Int, deltaKnowsDeviancy: Boolean): Amanda = updateAmanda(DeltaAmanda(deltaMeter, deltaKnowsDeviancy))

  private def updateMeter(n: Int): Amanda = {
    val newMeter = meter + n
    if (newMeter > maxValue) Amanda(maxValue, knowsDeviancy)
    else if (newMeter < minValue) Amanda(minValue, true)
    else Amanda(newMeter, knowsDeviancy)
  }

  private def updateKnowsDeviancy(b: Boolean): Amanda = Amanda(meter, knowsDeviancy || b)

}

case class DeltaAmanda(deltaMeter: Int, deltaKnowsDeviancy: Boolean = false)
