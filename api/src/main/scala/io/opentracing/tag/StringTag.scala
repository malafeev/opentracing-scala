package io.opentracing.tag

import io.opentracing.Span

class StringTag( val key2: String) extends AbstractTag[String](key2) {
  override def set(span: Span, tagValue: String): Unit = {
    span.setTag(key, tagValue)
  }

  def set(span: Span, tag: StringTag): Unit = {
    span.setTag(key, tag.key)
  }
}
