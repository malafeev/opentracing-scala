package io.opentracing


trait SpanContext {

  def baggageItems: Map[String, String]

}
