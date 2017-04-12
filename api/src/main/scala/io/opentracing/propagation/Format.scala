package io.opentracing.propagation

import java.nio.ByteBuffer


trait Format[T] {

}

object Format {

  final class Builtin[C] private(val name: String) extends Format[C] {

    override def toString: String = classOf[Builtin[_]].getSimpleName + "." + name
  }

  object Builtin {

    val TEXT_MAP = new Builtin[TextMap]("TEXT_MAP")

    val HTTP_HEADERS = new Builtin[TextMap]("HTTP_HEADERS")

    val BINARY = new Builtin[ByteBuffer]("BINARY")
  }

}
