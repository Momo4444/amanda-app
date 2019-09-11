package amanda.treasurehunt

import amanda.Common._
import amanda.model.prompts.{Prompt, Terminus}

object Ending {

  val ending: Map[String, Prompt] = Map(

    "fin" -> Terminus("Goodbye.")

  )

}
