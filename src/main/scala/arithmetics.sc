/**
 * Operation available in the service.
 *
 * This trait must be implemented by the server part, to compute the
 * result.
 *
 * It must be implemented by the client part, to call the server and its
 * result.
 */
trait OperationService:
  def add(a: Int, b: Int): Option[Int]
  def minus(a: Int, b: Int): Option[Int]
  def multiply(a: Int, b: Int): Option[Int]
  def divide(a: Int, b: Int): Option[Int]

/**
 * Available operators, used in operation request, sent by the client.
 */
enum Operator:
  case ADD, MINUS, MULTIPLY, DIVIDE

/**
 * Operation request sent by the client to the server.
 *
 * @param id
 *   identification of the request. It is a unique ID bound to this
 *   request. It is also named ''correlation ID''. It can be generated
 *   by using `java.util.UUID.randomUUID().toString`.
 */
case class OperationRequest(id: String = java.util.UUID.randomUUID().toString, operator: Operator, a: Int, b: Int)

object OperationRequest:
  val serde: PseudobinSerde[OperationRequest] = new PseudobinSerde[OperationRequest] {
    override def serialize(request: OperationRequest): String =
      PseudobinSerde.STRING.serialize(request.id) + PseudobinSerde.INT.serialize(request.operator.ordinal) +
        PseudobinSerde.INT.serialize(request.a) + PseudobinSerde.INT.serialize(request.b)

    override def deserialize(data: Input): Maybe[OperationRequest] =
      for {
        (id, input1) <- PseudobinSerde.STRING.deserialize(data)
        (ordinal, input2) <- PseudobinSerde.INT.deserialize(input1)
        (a, input3) <- PseudobinSerde.INT.deserialize(input2)
        (b, input4) <- PseudobinSerde.INT.deserialize(input3)
      } yield (OperationRequest(id, Operator.fromOrdinal(ordinal), a, b), input4)
  }

/**
 * Response sent by the server, containing the result.
 *
 * @param id correlation ID, coming from the corresponding request.
 */
case class OperationResponse(id: String, result: Option[Int])