package br.com.viavarejo.sync.streaming.infrastructure.config

import java.util.Map

import scala.beans.BeanProperty

class Streaming(@BeanProperty var batchDuration: Long)  extends Serializable {
  override def toString = s"Streaming(batchDuration: $batchDuration)"
}
object Streaming  extends Serializable {
  def build(properties: Map[String, Any]) = {
    new Streaming(properties.getOrDefault("streaming.batchDuration", 1000).asInstanceOf[Int])
  }
}
