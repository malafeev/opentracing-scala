package io.opentracing.tag

import io.opentracing.Span


abstract class AbstractTag[T](val key: String) {
  def getKey: String = key

  protected def set(span: Span, tagValue: T): Unit
}
