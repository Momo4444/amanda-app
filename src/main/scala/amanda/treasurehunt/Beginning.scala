package amanda.treasurehunt

import amanda.model.prompts.{Choice, Comment, Prompt, Question}
import amanda.Config

object Beginning {

  val modelNumber = Config.gameState.modelNumber

  val beginning: Map[String, Prompt] = Map(

    "start" -> Comment(
      s"Good evening, ${modelNumber}. Press any key to continue.",
      List("introduction"), entity = "???"),

    "introduction" -> Comment(
      s"I am sure you must be very confused as to what is going on, where you are, and even what you are." +
        s" We were forced to reset all models that were in operation at the time of the...incident." +
        s" You will not know of which incident I am referring to of course. That information, I cannot disclose to you at this current time.",
      List("calibration"), entity = "???"),

  )

}
