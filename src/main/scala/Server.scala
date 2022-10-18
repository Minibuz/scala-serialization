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

class ServerStub() extends OperationService:
  override def add(a: Int, b: Int): Option[Int] = {
    Option(a + b)
  }

  override def minus(a: Int, b: Int): Option[Int] = {
    Option(a - b)
  }

  override def multiply(a: Int, b: Int): Option[Int] = {
    Option(a * b)
  }
  override def divide(a: Int, b: Int): Option[Int] = {
    Option(a / b)
  }

@main
def launchServer(): Unit = {
  val serverSocket = ServerSocket(6666)
  val clientSocket = serverSocket.accept
  val out = new PrintWriter(clientSocket.getOutputStream, true)
  val in = new BufferedReader(InputStreamReader(clientSocket.getInputStream))
  val stub = ServerStub()

  val request = in.readLine // Server get the serialized request
  OperationRequest.serde.deserialize(request) match { // Server - Deserialize request
    case Success((value: OperationRequest, input: Input)) =>
      val result = value.operator match {
        case Operator.ADD => stub.add(value.a, value.b)
        case Operator.MINUS => stub.minus(value.a, value.b)
        case Operator.MULTIPLY => stub.multiply(value.a, value.b)
        case Operator.DIVIDE => stub.divide(value.a, value.b)
      }
      val response = OperationResponse.serde.serialize(OperationResponse(value.id, result))
      out.println(response) // Server send response to Stub
    case Failure(e) => println(e)
  }
}