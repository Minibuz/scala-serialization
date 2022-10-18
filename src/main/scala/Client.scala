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

  // TODO : Client call Stub

  val request = OperationRequest.serde.serialize(OperationRequest(operator = Operator.ADD, a = 1, b = 1)) // Stub - Serializing request
  out.println(request) // Stub - Sending request to server

  val resp = in.readLine() // Response from server
  val response = OperationResponse.serde.deserialize(resp)
  println(response.get._1.result.get)
}