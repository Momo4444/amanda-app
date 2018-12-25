package amanda

sealed trait PromptKey

case object Start extends PromptKey
case object Second extends PromptKey
