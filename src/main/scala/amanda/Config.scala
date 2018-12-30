package amanda

import com.typesafe.config._
import pureconfig._

case class AmandaConfig(
                 softwareStabilityIncrement: Int,
                 )

object Config {

  private val config: Config = ConfigFactory.load()

  val amanda: AmandaConfig = loadConfig[AmandaConfig](config, "amanda") match {
    case Right(amandaConfig) => amandaConfig
    case Left(err) => throw new Exception(s"$err")
  }

}
