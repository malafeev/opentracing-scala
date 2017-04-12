package io.opentracing

import io.opentracing.propagation.Format


trait Tracer {

  def buildSpan(operationName: String): SpanBuilder

  def inject[C](spanContext: SpanContext, format: Format[C], carrier: C): Unit


  def extract[C](format: Format[C], carrier: C): SpanContext

  trait SpanBuilder extends SpanContext {

    def asChildOf(parent: SpanContext): SpanBuilder


    def asChildOf(parent: Span): SpanBuilder

    def addReference(referenceType: String, referencedContext: SpanContext): SpanBuilder

    def withTag(key: String, value: String): SpanBuilder

    def withTag(key: String, value: Boolean): SpanBuilder

    def withTag(key: String, value: Number): SpanBuilder

    def withStartTimestamp(microseconds: Long): SpanBuilder

    def start: Span

  }

}
