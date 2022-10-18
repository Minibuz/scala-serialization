import scala.util.{Failure, Success, Try}
import java.net._
import java.io._

import java.io.BufferedReader
import java.io.PrintWriter
import java.net.ServerSocket

import java.io.BufferedReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket

@main
def launchClient(): Unit = {
  val clientSocket = Socket("127.0.0.1", 6666)
  val out = new PrintWriter(clientSocket.getOutputStream, true)
  val in = new BufferedReader(InputStreamReader(clientSocket.getInputStream))
  out.println("hello server")
  val resp = in.readLine()
  println(resp)

  /*
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
  */
}