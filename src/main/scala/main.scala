import scala.util.{Failure, Success, Try}

@main
def main(): Unit = {
  val messageStr = Message.serde.serialize(Message("hello", 1))
  Message.serde.deserialize(messageStr) match {
    case Success((value: Message, input: Input)) => println(value.toString + "\n" + input)
    case Failure(e) => println(e)
  }
}
