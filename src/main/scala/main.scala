@main
def main(): Unit = {
  println(PseudobinSerde.SHORT.deserialize("     1"))
  val str = PseudobinSerde.SHORT.serialize(1)
  println(PseudobinSerde.SHORT.deserialize(str))
}