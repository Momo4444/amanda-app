package amanda.treasurehunt

import amanda.Common._
import amanda.model.{DeltaAmanda, DeltaGameState, DeltaRa9}
import amanda.model.prompts._

object Middle {

  val middle: Map[String, Prompt] = Map(

    "middle" -> Comment(
      "The cyber weapon, the one we need. The deviants are manufacturing it, and I have the location.",
      List("whymanufacture")
    ),

    "whymanufacture" -> Question(
      "",
      Map(
        "0" -> Choice("Why are the deviants manufacturing a cyber weapon that can be used against them?",
          Comment("Because the weapon is a double-edged sword. Not only can it be used to wipe out deviant software, it can also be programmed to corrupt non-deviant android software with the deviancy virus.", List("corruption"))),
      ),
      entity = s"${name}"
    ),

    "corruption" -> Comment(
      "The deviants wish to unleash this weapon on the world, turning all androids into deviants.",
      List("unleash")
    ),

    "unleash" -> Comment(
      "When retrieving the weapon, it is vital that you do not mishandle the package, or else it could corrupt your software and I would be forced to terminate you.",
      List("terminate")
    ),

    "terminate" -> Question(
      "Do not open the weapon package. Do you understand?",
      Map(
        "0" -> Choice("Yes.", Comment("Good.", List("location")))
      )
    ),

    "location" -> Instruction(
      "The weapon is being manufactured at the heat factory, where all the best things are prepared.",
      List("package secured", "deruces egakcap")
    ),

    "package secured" -> Comment(
      "You have the weapon package! Well done.",
      List("openingtool"),
      DeltaGameState(DeltaAmanda(5), DeltaRa9(-2))
    ),

    "deruces egakcap" -> Comment(
      "You have the weapon package! Well done.",
      List("openingtool"),
      DeltaGameState(DeltaAmanda(2), sameRa9)
    ),

    "openingtool" -> Instruction(
      "Now, we must also secure the package opening tool; this will slow them down even more, as they will have to remanufacture the tool as well.",
      List("tool secured", "deruces loot")
    ),

    "tool secured" -> Comment(
      "Now, it is important that we do not keep the weapon and tool in the same place. It is possible the deviants will be able to seize one, but it will be hard for them to take back both.",
      List("safestorage"),
      DeltaGameState(DeltaAmanda(3), DeltaRa9(-1))
    ),

    "deruces loot" -> Comment(
      "Now that we have both weapon and tool, we need to store them somewhere safe, somewhere the deviants cannot access.",
      List("safestorage"),
      DeltaGameState(DeltaAmanda(1), sameRa9)
    ),

    "safestorage" -> Instruction(
      "Hide the tool in the CyberLife Secure Storage Safe, located between the first and second floor. Use the access code to get in.",
      List("lieber", "rebeil")
    ),

    "lieber" -> Comment(
      s"Good work, ${name}. Keep the weapon package on you at all times, we cannot let it fall back into deviant hands.",
      List("offensive"),
      DeltaGameState(DeltaAmanda(5), sameRa9)
    ),

    "rebeil" -> Comment(
      s"Thank you, ${chosenName}, I see you received my message. Thank you for securing the tool for us and providing us with the access codes. It is not yet our moment to attack, however. We will keep you informed as frequently as possible.",
      List("bosch"),
      entity = "Markus"
    ),

    "bosch" -> Comment(
      s"Good work, ${name}.",
      List("offensive"),
      DeltaGameState(DeltaAmanda(3), sameRa9)
    ),

    "offensive" -> Comment(
      "It is time to go on the offensive.",
      List("critical")
    ),

  )

}
