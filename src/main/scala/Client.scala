import scala.util.{Failure, Success, Try}

@main
def main(): Unit = {
  // TODO : Client call Stub
  val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.ADD, a = 1, b = 1)) // Stub
  // TODO : Stub send request to server
  // TODO : Server get the serialized request
  OperationRequest.serde.deserialize(request) match { // Server
    case Success((value: OperationRequest, input: Input)) => value.operator match {
      case Operator.ADD => print(value.a + value.b)
      case Operator.MINUS => print(value.a - value.b)
      case Operator.MULTIPLY => print(value.a * value.b)
      case Operator.DIVIDE => print(value.a / value.b)
    }
    case Failure(e) => println(e)
  }
}