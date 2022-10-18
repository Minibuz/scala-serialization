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

class ClientStub(clientSocket : Socket, out : PrintWriter, in : BufferedReader) extends OperationService:
  override def add(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.ADD, a = a, b = b)) // Stub - Serializing request
    sendRequest(request)
  }

  override def minus(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.MINUS, a = a, b = b)) // Stub - Serializing request
    sendRequest(request)
  }

  override def multiply(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.MULTIPLY, a = a, b = b)) // Stub - Serializing request
    sendRequest(request)
  }
  override def divide(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.DIVIDE, a = a, b = b)) // Stub - Serializing request
    sendRequest(request)
  }

  def sendRequest(request : String): Option[Int] = {
    out.println(request) // Stub - Sending request to server
    val resp = in.readLine() // Response from server
    val response = OperationResponse.serde.deserialize(resp)
    response.get._1.result
  }

@main
def launchClient(): Unit = {
  val clientSocket = Socket("127.0.0.1", 6666)
  val out = PrintWriter(clientSocket.getOutputStream, true)
  val in = BufferedReader(InputStreamReader(clientSocket.getInputStream))

  println(ClientStub(clientSocket, out, in).add(1, 1).get)
}