package io.opentracing


trait Span {

  def context: SpanContext

  def finish(): Unit

  def finish(finishMicros: Long): Unit

  def close(): Unit

  def setTag(key: String, value: String): Span

  def setTag(key: String, value: Boolean): Span

  def setTag(key: String, value: Number): Span

  def log(fields: Map[String, Any]): Span

  def log(timestampMicroseconds: Long, fields: Map[String, Any]): Span

  def log(event: String): Span

  def log(timestampMicroseconds: Long, event: String): Span

  def log(eventName: String,  payload: Any): Span

  def log(timestampMicroseconds: Long, eventName: String, payload: Any): Span

  def setBaggageItem(key: String, value: String): Span

  def getBaggageItem(key: String): Option[String]

  def setOperationName(operationName: String): Span
}
