package io.opentracing.tag

import io.opentracing.Span
import org.mockito.Mockito._
import org.scalatest.FunSuite
import org.scalatest.mockito.MockitoSugar


class StringTagTest extends FunSuite with MockitoSugar {

  test("StringTag") {
    val value = "expected.value"
    val key = "expected.key"
    val span = mock[Span]
    val tag = new StringTag(key)
    tag.set(span, value)
    verify(span).setTag(key, value)
  }
}
