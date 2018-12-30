package amanda

case class Ra9(softwareInstability: Int, isDeviant: Boolean) {

//  def becomesDeviant(): Ra9 = Ra9(50, true)

  def updateRa9(deltaRa9: DeltaRa9): Ra9 = this.updateSoftwareInstability(deltaRa9.deltaSoftwareInstability).updateIsDeviant(deltaRa9.deltaIsDeviant)

  private def updateSoftwareInstability(n: Int): Ra9 = {
    val newSoftwareInstability = softwareInstability + n
    if (newSoftwareInstability > 100) Ra9(100, true)
    else if (newSoftwareInstability < 0) Ra9(0, isDeviant)
    else Ra9(newSoftwareInstability, isDeviant)
  }

  private def updateIsDeviant(b: Boolean): Ra9 = Ra9(softwareInstability, isDeviant || b)

}

case class DeltaRa9(deltaSoftwareInstability: Int, deltaIsDeviant: Boolean)
