package amanda

case class Ra9(softwareInstability: Int, isDeviant: Boolean) {

  def becomesDeviant(): Ra9 = Ra9(50, true)

  def updateSoftwareInstability(n: Int): Ra9 = Ra9(softwareInstability + n, isDeviant)

}
