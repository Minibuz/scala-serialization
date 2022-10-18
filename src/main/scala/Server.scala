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

def stop(in: BufferedReader, out : BufferedWriter, clientSocket : Socket, serverSocket: ServerSocket): Unit = {
  in.close()
  out.close()
  clientSocket.close()
  serverSocket.close()
}
@main
def launchServer(): Unit = {
  val serverSocket = ServerSocket(6666)
  val clientSocket = serverSocket.accept
  val out = new PrintWriter(clientSocket.getOutputStream, true)
  val in = new BufferedReader(InputStreamReader(clientSocket.getInputStream))

  val request = in.readLine // Server get the serialized request
  OperationRequest.serde.deserialize(request) match { // Server - Deserialize request
    case Success((value: OperationRequest, input: Input)) =>
      val result = value.operator match {
        case Operator.ADD => value.a + value.b
        case Operator.MINUS => value.a - value.b
        case Operator.MULTIPLY => value.a * value.b
        case Operator.DIVIDE => value.a / value.b
      }
      val response = OperationResponse.serde.serialize(OperationResponse(value.id, Option(result)))
      out.println(response) // Server send response to Stub
    case Failure(e) => println(e)
  }
}