import scala.util.{Failure, Success, Try}

@main
def launchClient(): Unit = {
  // TODO : Client call Stub
  val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.ADD, a = 1, b = 1)) // Stub
  // TODO : Stub send request to server
  // TODO : Server get the serialized request
  OperationRequest.serde.deserialize(request) match { // Server
    case Success((value: OperationRequest, input: Input)) =>
      val result = value.operator match {
        case Operator.ADD => value.a + value.b
        case Operator.MINUS => value.a - value.b
        case Operator.MULTIPLY => value.a * value.b
        case Operator.DIVIDE => value.a / value.b
      }
      val response = OperationResponse.serde.serialize(OperationResponse(value.id, Option(result)))
      // TODO : Server send response to Stub
    case Failure(e) => println(e)
  }
}