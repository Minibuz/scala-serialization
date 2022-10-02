import org.scalatest.funsuite.AnyFunSuite

import scala.util.Success

class SerializationTest extends AnyFunSuite {

  test("Serialization and Deserialization for INT = 1") {
    val strForInt = PseudobinSerde.INT.serialize(1)
    assert(strForInt == "          1")
    val int = PseudobinSerde.INT.deserialize(strForInt)
    assert(int == Success(1, Input(strForInt, 11)))
  }

  test("Serialization and Deserialization for INT = 9999") {
    val strForInt = PseudobinSerde.INT.serialize(9999)
    assert(strForInt == "       9999")
    val int = PseudobinSerde.INT.deserialize(strForInt)
    assert(int == Success(9999, Input(strForInt, 11)))
  }

  test("Serialization and Deserialization for SHORT = 1") {
    val strForShort = PseudobinSerde.SHORT.serialize(1)
    assert(strForShort == "     1")
    val short = PseudobinSerde.SHORT.deserialize(strForShort)
    assert(short == Success(1, Input(strForShort, 6)))
  }

  test("Serialization and Deserialization for SHORT = 9999") {
    val strForShort = PseudobinSerde.SHORT.serialize(9999)
    assert(strForShort == "  9999")
    val short = PseudobinSerde.SHORT.deserialize(strForShort)
    assert(short == Success(9999, Input(strForShort, 6)))
  }

  test("Serialization and Deserialization for LONG = 1") {
    val strForLong = PseudobinSerde.LONG.serialize(1)
    assert(strForLong == "                   1")
    val long = PseudobinSerde.LONG.deserialize(strForLong)
    assert(long == Success(1, Input(strForLong, 20)))
  }

  test("Serialization and Deserialization for LONG = 9999") {
    val strForLong = PseudobinSerde.LONG.serialize(9999)
    assert(strForLong == "                9999")
    val long = PseudobinSerde.LONG.deserialize(strForLong)
    assert(long == Success(9999, Input(strForLong, 20)))
  }

  test("Serialization and Deserialization for DOUBLE = 1.1") {
    val strForDouble = PseudobinSerde.DOUBLE.serialize(1.1)
    assert(strForDouble == "                     1.1")
    val double = PseudobinSerde.DOUBLE.deserialize(strForDouble)
    assert(double == Success(1.1, Input(strForDouble, 24)))
  }

  ignore("Serialization and Deserialization for DOUBLE = -1e1") {
    val strForDouble = PseudobinSerde.DOUBLE.serialize(-1e1)
    assert(strForDouble == "                    -1e1")
    val double = PseudobinSerde.DOUBLE.deserialize(strForDouble)
    assert(double == Success(-1e1, Input(strForDouble, 24)))
  }

  test("Serialization and Deserialization for BOOLEAN = true") {
    val strForBoolean = PseudobinSerde.BOOLEAN.serialize(true)
    assert(strForBoolean == " true")
    val boolean = PseudobinSerde.BOOLEAN.deserialize(strForBoolean)
    assert(boolean == Success(true, Input(strForBoolean, 5)))
  }

  test("Serialization and Deserialization for BOOLEAN = False") {
    val strForBoolean = PseudobinSerde.BOOLEAN.serialize(false)
    assert(strForBoolean == "false")
    val boolean = PseudobinSerde.BOOLEAN.deserialize(strForBoolean)
    assert(boolean == Success(false, Input(strForBoolean, 5)))
  }

  test("Serialization and Deserialization for String = \"Test\"") {
    val strForString = PseudobinSerde.STRING.serialize("Test")
    assert(strForString == "     4Test")
    val str = PseudobinSerde.STRING.deserialize(strForString)
    assert(str == Success("Test", Input(strForString, 10)))
  }
}
