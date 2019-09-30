package br.com.bigdata.config

import java.util.{Map}
import br.com.viavarejo.sync.streaming.infrastructure.config.{HadoopConfiguration, Streaming}
import scala.beans.BeanProperty


class ApplicationConfig {

  @BeanProperty
  var streaming: Streaming = _

  @BeanProperty
  var hadoopConfiguration: HadoopConfiguration = _

  def this(name: String, properties: Map[String, Any]) {
    this()
    this.hadoopConfiguration = HadoopConfiguration.build("hadoopConfiguration", properties)
  }

  override def toString = s"Application(streaming: $streaming)"
}
