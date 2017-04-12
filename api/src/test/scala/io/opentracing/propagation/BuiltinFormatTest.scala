package io.opentracing.propagation

import org.scalatest.FunSuite


class BuiltinFormatTest extends FunSuite {

  test("test Builtin") {
    assert(Format.Builtin.HTTP_HEADERS.toString == "Builtin.HTTP_HEADERS")
    assert(Format.Builtin.TEXT_MAP.toString == "Builtin.TEXT_MAP")
    assert(Format.Builtin.BINARY.toString == "Builtin.BINARY")
  }

}
