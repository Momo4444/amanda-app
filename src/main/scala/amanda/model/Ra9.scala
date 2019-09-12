package amanda.model

import amanda.Config

case class Ra9(softwareInstability: Int, isDeviant: Boolean) {

  private val minValue = Config.ra9.minValue
  private val maxValue = Config.ra9.maxValue

  def updateRa9(deltaRa9: DeltaRa9): Ra9 = this.updateSoftwareInstability(deltaRa9.deltaSoftwareInstability).updateIsDeviant(deltaRa9.deltaIsDeviant)

  def updateRa9(deltaSoftwareInstability: Int): Ra9 = updateRa9(DeltaRa9(deltaSoftwareInstability))
  def updateRa9(deltaIsDeviant: Boolean): Ra9 = updateRa9(DeltaRa9(0, deltaIsDeviant))
  def updateRa9(deltaSoftwareInstability: Int, deltaIsDeviant: Boolean): Ra9 = updateRa9(DeltaRa9(deltaSoftwareInstability, deltaIsDeviant))

  private def updateSoftwareInstability(n: Int): Ra9 = {
    val newSoftwareInstability = softwareInstability + n
    if (newSoftwareInstability > maxValue) Ra9(maxValue, true)
    else if (newSoftwareInstability < minValue) Ra9(minValue, isDeviant)
    else Ra9(newSoftwareInstability, isDeviant)
  }

  private def updateIsDeviant(b: Boolean): Ra9 = Ra9(softwareInstability, isDeviant || b)

}

case class DeltaRa9(deltaSoftwareInstability: Int, deltaIsDeviant: Boolean = false)
