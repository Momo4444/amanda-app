package amanda

case class Amanda(meter: Int, knowsDeviancy: Boolean) {

  def foundDeviancy(): Amanda = Amanda(0, true)

  def updateMeter(n: Int): Amanda = Amanda(meter + n, knowsDeviancy)

}
