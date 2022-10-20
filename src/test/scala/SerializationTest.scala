import org.scalatest.funsuite.AnyFunSuite

import scala.util.Success
import scala.util.Failure

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

  test("Deserialization for INT with less with less than 11 characters") {
    val strForInt = "     9"
    val int = PseudobinSerde.INT.deserialize(strForInt)
    assertThrows[StringIndexOutOfBoundsException](int.get)
  }

  test("Deserialization for INT with letter") {
    val strForInt = "          D"
    val int = PseudobinSerde.INT.deserialize(strForInt)
    assertThrows[NumberFormatException](int.get)
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

  test("Deserialization for SHORT with less with less than 6 characters") {
    val strForShort = "   9"
    val short = PseudobinSerde.SHORT.deserialize(strForShort)
    assertThrows[StringIndexOutOfBoundsException](short.get)
  }

  test("Deserialization for SHORT with letter") {
    val strForShort = "     D"
    val short = PseudobinSerde.SHORT.deserialize(strForShort)
    assertThrows[NumberFormatException](short.get)
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

  test("Deserialization for LONG with less with less than 20 characters") {
    val strForLONG = "          58"
    val long = PseudobinSerde.LONG.deserialize(strForLONG)
    assertThrows[StringIndexOutOfBoundsException](long.get)
  }

  test("Deserialization for LONG with letter") {
    val strForLONG = "                  AZ"
    val long = PseudobinSerde.LONG.deserialize(strForLONG)
    assertThrows[NumberFormatException](long.get)
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

  test("Deserialization for DOUBLE with less with less than 24 characters") {
    val strForDOUBLE = "                  85"
    val double = PseudobinSerde.DOUBLE.deserialize(strForDOUBLE)
    assertThrows[StringIndexOutOfBoundsException](double.get)
  }

  test("Deserialization for DOUBLE with letter") {
    val strForDOUBLE = "                      TOTO"
    val double = PseudobinSerde.DOUBLE.deserialize(strForDOUBLE)
    assertThrows[NumberFormatException](double.get)
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

  test("Deserialization for BOOLEAN with invalid String") {
    val strForBoolean = "TRUEE"
    val boolean = PseudobinSerde.BOOLEAN.deserialize(strForBoolean)
    assertThrows[IllegalArgumentException](boolean.get)
  }

  test("Serialization and Deserialization for String = \"Test\"") {
    val strForString = PseudobinSerde.STRING.serialize("Test")
    assert(strForString == "     4Test")
    val str = PseudobinSerde.STRING.deserialize(strForString)
    assert(str == Success("Test", Input(strForString, 10)))
  }

  test("Serialization and Deserialization for List<String>(\"A\",\"B\")") {
    val strForString = PseudobinSerde.ARRAY(PseudobinSerde.STRING).serialize(List("A","B"))
    assert(strForString == "     2     1A     1B")
    val str = PseudobinSerde.ARRAY(PseudobinSerde.STRING).deserialize(strForString)
    assert(str == Success(List("A","B"), Input(strForString, 20)))
  }

  test("Serialization and Deserialization for Nullable<String>: None") {
    val strForNullableNone = PseudobinSerde.NULLABLE(PseudobinSerde.STRING).serialize(None)
    assert(strForNullableNone == "0")
    val str = PseudobinSerde.NULLABLE(PseudobinSerde.STRING).deserialize(strForNullableNone)
    assert(str == Success(None, Input(strForNullableNone, 1)))
  }

  test("Serialization and Deserialization for Nullable<String>: Some(\"A\")") {
    val strForNullableSome = PseudobinSerde.NULLABLE(PseudobinSerde.STRING).serialize(Some("A"))
    assert(strForNullableSome == "1     1A")
    val str = PseudobinSerde.NULLABLE(PseudobinSerde.STRING).deserialize(strForNullableSome)
    assert(str == Success(Some("A"), Input(strForNullableSome, 8)))
  }
}
