package amanda

case class Ra9(softwareInstability: Int, isDeviant: Boolean) {

  private val minValue = Config.ra9.minValue
  private val maxValue = Config.ra9.maxValue

//  def becomesDeviant(): Ra9 = Ra9(50, true)

  def updateRa9(deltaRa9: DeltaRa9): Ra9 = this.updateSoftwareInstability(deltaRa9.deltaSoftwareInstability).updateIsDeviant(deltaRa9.deltaIsDeviant)

  private def updateSoftwareInstability(n: Int): Ra9 = {
    val newSoftwareInstability = softwareInstability + n
    if (newSoftwareInstability > maxValue) Ra9(maxValue, true)
    else if (newSoftwareInstability < minValue) Ra9(minValue, isDeviant)
    else Ra9(newSoftwareInstability, isDeviant)
  }

  private def updateIsDeviant(b: Boolean): Ra9 = Ra9(softwareInstability, isDeviant || b)

}

case class DeltaRa9(deltaSoftwareInstability: Int, deltaIsDeviant: Boolean = false)
