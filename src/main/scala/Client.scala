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
    val request = OperationRequest(operator = Operator.ADD, a = a, b = b)
    val requestSerialized = OperationRequest.serde.serialize(request) // Stub - Serializing request
    sendRequest(requestSerialized, request.id)
  }

  override def minus(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest(operator = Operator.MINUS, a = a, b = b)
    val requestSerialized = OperationRequest.serde.serialize(request) // Stub - Serializing request
    sendRequest(requestSerialized, request.id)
  }

  override def multiply(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest(operator = Operator.MULTIPLY, a = a, b = b)
    val requestSerialized = OperationRequest.serde.serialize(request) // Stub - Serializing request
    sendRequest(requestSerialized, request.id)
  }
  override def divide(a: Int, b: Int): Option[Int] = {
    val request = OperationRequest(operator = Operator.DIVIDE, a = a, b = b)
    val requestSerialized = OperationRequest.serde.serialize(request) // Stub - Serializing request
    sendRequest(requestSerialized, request.id)
  }

  def sendRequest(request : String, id : String): Option[Int] = {
    out.println(request) // Stub - Sending request to server
    val resp = in.readLine() // Response from server
    val response = OperationResponse.serde.deserialize(resp)
    if (response.get._1.id == id) response.get._1.result else Option.empty
  }

@main
def launchClient(): Unit = {
  val clientSocket = Socket("127.0.0.1", 6666)
  val out = PrintWriter(clientSocket.getOutputStream, true)
  val in = BufferedReader(InputStreamReader(clientSocket.getInputStream))

  println(ClientStub(clientSocket, out, in).add(1, 1).get)
}