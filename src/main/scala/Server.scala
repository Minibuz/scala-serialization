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
  val greeting = in.readLine
  if ("hello server" == greeting) out.println("hello client")
  else out.println("unrecognised greeting")
}