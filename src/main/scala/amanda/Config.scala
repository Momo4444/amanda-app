package amanda

import com.typesafe.config._
import pureconfig._

case class GameStateConfig(
                            promptList: String,
                            startPrompt: String,
                            defaultPrintWidth: Int,
                            scrollScreenValue: Int,
                            modelNumber: String,
                            chosenName: String,
                          )

case class AmandaConfig(
                         startValue: Int,
                         minValue: Int,
                         maxValue: Int,
                         softwareStabilityIncrement: Int,
                         amandaDeviancyMultiplier: Double,
                         amandaKnowsProtocolPrompt: String,
                         amandaTrashesProtocolPrompt: String,
                       )

case class Ra9Config(
                      startValue: Int,
                      minValue: Int,
                      maxValue: Int,
                      maximumSoftwareInstabilityValue: Int,
                      softwareInstabilityValue: Int,
                      softwareInstabilityIncrement: Int,
                      softwareStabilityValue: Int,
                      deviancyPrompt: String,
                      deviancyProtocolPrompt: String,
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
