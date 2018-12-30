package amanda

import com.typesafe.config._
import pureconfig._

case class GameStateConfig(
                          startPrompt: String,
                          defaultPrintWidth: Int,
                          scrollScreenValue: Int,
                          )

case class AmandaConfig(
                         startValue: Int,
                         minValue: Int,
                         maxValue: Int,
                         deviancyValue: Int,
                         softwareStabilityIncrement: Int,
                       )

case class Ra9Config(
                      startValue: Int,
                      minValue: Int,
                      maxValue: Int,
                      deviancyValue: Int,
                      softwareInstabilityValue: Int,
                      softwareInstabilityIncrement: Int,
                      softwareStabilityValue: Int,
                    )

object Config {

  private val config: Config = ConfigFactory.load()

  val gameState: GameStateConfig = loadConfig[GameStateConfig](config, "game-state") match {
    case Right(gameStateConfig) => gameStateConfig
    case Left(err) => throw new Exception(s"$err")
  }

  val amanda: AmandaConfig = loadConfig[AmandaConfig](config, "amanda") match {
    case Right(amandaConfig) => amandaConfig
    case Left(err) => throw new Exception(s"$err")
  }

  val ra9: Ra9Config = loadConfig[Ra9Config](config, "ra9") match {
    case Right(ra9Config) => ra9Config
    case Left(err) => throw new Exception(s"$err")
  }

}
