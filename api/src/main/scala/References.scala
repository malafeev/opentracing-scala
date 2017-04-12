package io.opentracing

object References {
  /**
    * See http://opentracing.io/spec/#causal-span-references for more information about CHILD_OF references
    */
  val CHILD_OF = "child_of"
  /**
    * See http://opentracing.io/spec/#causal-span-references for more information about FOLLOWS_FROM references
    */
  val FOLLOWS_FROM = "follows_from"
}

