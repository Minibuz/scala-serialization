import scala.util.Try

case class Input(data: String, offset: Int):
  def current(n: Int): String = data.substring(offset, offset + n)
  def next(n: Int): Input = Input(data, offset + n)

type Maybe[A] = Try[(A, Input)]

trait PseudobinSerde[A]:
  def serialize(value: A): String
  def deserialize(data: Input): Maybe[A]
  def deserialize(data: String): Maybe[A] = deserialize(Input(data, 0))

object PseudobinSerde:
  val INT: PseudobinSerde[Int] = new PseudobinSerde[Int] {
    override def serialize(value: Int): String =
      " ".repeat(11 - s"$value".length).concat(s"$value")
    override def deserialize(data: Input): Maybe[Int] =
      Try(data.current(11).trim.toInt, Input(data.data, data.offset + 11))
  }

  val SHORT: PseudobinSerde[Short] = new PseudobinSerde[Short] {
    override def serialize(value: Short): String =
      " ".repeat(6 - s"$value".length).concat(s"$value")
    override def deserialize(data: Input): Maybe[Short] =
      Try(data.current(6).trim.toShort, Input(data.data, data.offset + 6))
  }

  val LONG: PseudobinSerde[Long] = new PseudobinSerde[Long] {
    override def serialize(value: Long): String =
      " ".repeat(20 - s"$value".length).concat(s"$value")
    override def deserialize(data: Input): Maybe[Long] =
      Try(data.current(20).trim.toLong, Input(data.data, data.offset + 20))
  }
  
  val DOUBLE: PseudobinSerde[Double] = new PseudobinSerde[Double] {
    override def serialize(value: Double): String =
      " ".repeat(24 - s"$value".length).concat(s"$value")
    override def deserialize(data: Input): Maybe[Double] =
      Try(data.current(24).trim.toDouble, Input(data.data, data.offset + 24))
  }
  
  val BOOLEAN: PseudobinSerde[Boolean] = new PseudobinSerde[Boolean] {
    override def serialize(value: Boolean): String =
      " ".repeat(5 - s"$value".length).concat(s"$value")
    override def deserialize(data: Input): Maybe[Boolean] =
      Try(data.current(5).trim.toBoolean, Input(data.data, data.offset + 5))
  }
  
  val STRING: PseudobinSerde[String] = new PseudobinSerde[String] {
    override def serialize(value: String): String = {
      val size = SHORT.serialize(value.length.toShort)
      size.concat(value)
    }
    override def deserialize(data: Input): Maybe[String] = {
      val size = SHORT.deserialize(data)
      Try(data.next(6).current(size.get._1), Input(data.data, data.offset + 6 + size.get._1))
    }
  }

  def ARRAY[A](itemSerde: PseudobinSerde[A]): PseudobinSerde[List[A]] = new PseudobinSerde[List[A]] {
    override def serialize(value: List[A]): String = ???
    override def deserialize(data: Input): Maybe[List[A]] = ???
  }

  def NULLABLE[A](itemSerde: PseudobinSerde[A]): PseudobinSerde[Option[A]] = ???