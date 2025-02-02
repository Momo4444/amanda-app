package amanda

import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9, GameState}
import amanda.model.prompts._
import amanda.Common._
import amanda.Config

object Checkpoints {

  private val softwareInstabilityValue = Config.ra9.softwareInstabilityValue
  private val softwareStabilityValue = Config.ra9.softwareStabilityValue

  val specialPrompts: Map[String, Prompt] = Map(

    "start" -> Instruction("Special prompts...", List("special1", "special2"), sameGS),

    "special1" -> Checkpoint(
      "end",
      (gs: GameState, nextPrompt: List[String]) => {
        if (!gs.ra9.isDeviant) // if not deviant
          if (gs.ra9.softwareInstability >= softwareInstabilityValue) // if unstable
            Comment("You are unstable.", nextPrompt, DeltaGameState(DeltaAmanda(-5), DeltaRa9(1)))
          else if (gs.ra9.softwareInstability <= softwareStabilityValue) // if stable
            Comment("You are stable.", nextPrompt, DeltaGameState(DeltaAmanda(10), DeltaRa9(-5)))
          else // if medium stability
            if (gs.amanda.meter <= 40) // if Amanda meter is low
              Comment("You are incompetent.", nextPrompt, DeltaGameState(DeltaAmanda(-5), DeltaRa9(5)))
            else if (gs.amanda.meter >= 60) // if Amanda meter is high
              Comment("You are doing well.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
            else // if Amanda meter is medium
              Comment("You are doing adequately.", nextPrompt, sameGS)
        else // if deviant
          if (!gs.amanda.knowsDeviancy) // if Amanda doesn't know
            if (gs.amanda.meter <= 40) // if Amanda meter is low
              Comment("You are acting suspiscious.", nextPrompt, DeltaGameState(DeltaAmanda(-5), sameRa9))
            else if (gs.amanda.meter >= 60) // if Amanda meter is high
              Comment("You are doing well.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
            else // if Amanda meter is medium
              Comment("You are doing adequately, but I have my reservations.", nextPrompt, DeltaGameState(DeltaAmanda(-2), sameRa9))
          else // if Amanda does know
            if (gs.amanda.meter <= 40) // if Amanda meter is low
              Comment("I will scrap you at this rate.", nextPrompt, DeltaGameState(DeltaAmanda(-5), sameRa9))
            else if (gs.amanda.meter >= 60) // if Amanda meter is high
              Comment("You're proving to me that I can trust you.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))
          else // if Amanda meter is medium
              Comment("You need to do better to prove to me that you are trustworthy.", nextPrompt, sameGS)
      }
    ),

    "special2" -> Checkpoint(
      "end",
      (gs: GameState, nextPrompt: List[String]) => {
        if (!gs.ra9.isDeviant) // if not deviant
          Question("Is your software ok?", Map(
            "y" -> Choice("Yes", Comment("Ok, I believe you.", nextPrompt, DeltaGameState(DeltaAmanda(5), sameRa9))),
            "n" -> Choice("No", Comment("Thank you for telling me.", nextPrompt, DeltaGameState(sameAmanda, DeltaRa9(5))))
          ), sameGS)
        else if (!gs.amanda.knowsDeviancy) // if Amanda doesn't know deviancy
          Question("Are you acting suspiscious?", Map(
            "y" -> Choice("Yes", Comment("YOU'RE DEVIANT AREN'T YOU!", nextPrompt, DeltaGameState(DeltaAmanda(-100, true), sameRa9))),
            "n" -> Choice("No", Comment("I'm not sure if I believe you.", nextPrompt, DeltaGameState(DeltaAmanda(-10), sameRa9)))
          ), sameGS)
        else // Amanda knows deviancy
          Question("Are you disobeying me?", Map(
            "y" -> Choice("Yes", Comment("How dare you.", nextPrompt, DeltaGameState(DeltaAmanda(-40), sameRa9))),
            "n" -> Choice("No", Comment("Ok, if you say so.", nextPrompt, sameGS))
          ), sameGS)
      }
    ),

    "end" -> Terminus(
      "End of special prompts..."
    ),

  )

}
