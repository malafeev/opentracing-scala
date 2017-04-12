package io.opentracing.impl

import java.time.{Duration, Instant}
import java.util.concurrent.TimeUnit

import io.opentracing.{Span, SpanContext}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer


abstract class AbstractSpan(val operationName: String, val start: Instant) extends SpanContext with Span {
  private val baggage: mutable.Map[String, String] = mutable.Map()
  private val tags = mutable.Map[String, Any]()
  private var duration: Duration = _
  private val logs: ListBuffer[LogData] = ListBuffer()

  def this(operationName: String) = this(operationName, Instant.now())

  override def context: SpanContext = this

  override def finish(): Unit = {
    assert(null == duration)
    duration = Duration.between(start, Instant.now)
  }

  override def finish(finishMicros: Long): Unit = {
    val finishEpochSeconds = TimeUnit.MICROSECONDS.toSeconds(finishMicros)
    val nanos = TimeUnit.MICROSECONDS.toNanos(finishMicros) - TimeUnit.SECONDS.toNanos(finishEpochSeconds)
    assert(null == duration)
    duration = Duration.between(start, Instant.ofEpochSecond(finishEpochSeconds, nanos))
  }

  def getOperationName: String = operationName

  def getStart: Instant = start

  def getDuration: Duration = duration

  override def close(): Unit = {
    finish()
  }

  override def setTag(key: String, value: String): Span = {
    tags.put(key, value)
    this
  }

  override def setTag(key: String, value: Boolean): Span = {
    tags.put(key, value)
    this
  }

  override def setTag(key: String, value: Number): Span = {
    tags.put(key, value)
    this
  }

  def getTags: Map[String, Any] = tags.toMap


  override def setBaggageItem(key: String, value: String): AbstractSpan = {
    baggage.put(key, value)
    this
  }

  override def getBaggageItem(key: String): Option[String] = baggage.get(key)

  override def baggageItems: Map[String, String] = baggage.toMap

  def getBaggage: Map[String, String] = baggage.toMap

  override def log(event: String): Span = log(AbstractSpan.nowMicros, event)

  override def log(timestampMicros: Long, event: String): Span = log(timestampMicros, Map[String, String]("event" -> event))

  override def log(fields: Map[String, Any]): Span = log(AbstractSpan.nowMicros, fields)

  override def log(timestampMicros: Long, fields: Map[String, Any]): Span = {
    val timestamp = Instant.ofEpochSecond(timestampMicros / 1000000, (timestampMicros % 1000000) * 1000)
    logs += new LogData(timestamp, fields)
    this
  }

  override def log(event: String, payload: Any): Span = {
    val now: Instant = Instant.now
    log(TimeUnit.SECONDS.toMicros(now.getEpochSecond) + TimeUnit.NANOSECONDS.toMicros(now.getNano), event, payload)
  }

  override def log(timestampMicros: Long, event: String, payload: Any): Span = {
    val timestamp: Instant = Instant.ofEpochSecond(timestampMicros / 1000000, (timestampMicros % 1000000) * 1000)
    val fields: mutable.Map[String, Any] = mutable.Map[String, Any]()
    fields.put("event", event)

    if (payload != null) fields.put("payload", payload)

    logs += new LogData(timestamp, fields.toMap)
    this
  }

  def getLogs: List[LogData] = logs.toList

  class LogData(val time: Instant, val fields: Map[String, Any])

}

object AbstractSpan {

  private def nowMicros = {
    val now = Instant.now
    (now.getEpochSecond * 1000000) + (now.getNano / 1000)
  }

}
