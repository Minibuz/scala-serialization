import scala.util.{Failure, Success, Try}

case class Message(content: String, criticality: Int)

object Message:
  val serde: PseudobinSerde[Message] = new PseudobinSerde[Message] {
      override def serialize(message: Message): String =
        PseudobinSerde.STRING.serialize(message.content) + PseudobinSerde.INT.serialize(message.criticality)

      override def deserialize(data: Input): Maybe[Message] =
        for {
          (content, input1) <- PseudobinSerde.STRING.deserialize(data)
          (criticality, input2) <- PseudobinSerde.INT.deserialize(input1)
        } yield (Message(content, criticality), input2)
    }