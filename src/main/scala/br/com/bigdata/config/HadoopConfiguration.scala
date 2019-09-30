package br.com.viavarejo.sync.streaming.infrastructure.config

import java.util
import java.util.{Base64, Map}

import org.apache.commons.lang3.StringUtils.{startsWith, substring, substringBetween}

import scala.beans.BeanProperty
import scala.collection.JavaConversions._

class HadoopConfiguration extends Serializable {

  @BeanProperty
  var overwite: Boolean = false

  @BeanProperty
  var kerberos: Kerberos = _

  @BeanProperty
  var files: util.Map[String, Any] = new util.HashMap[String, Any]()

  def isEnableKerberos(): Boolean = {
    Option(kerberos).nonEmpty
  }

  override def toString = s"HadoopConfiguration(overwite: $overwite, kerberos: $kerberos, properties: $files)"
}

object HadoopConfiguration  extends Serializable {

  def build(prefix: String, properties: Map[String, Any]): HadoopConfiguration = {

    val hadoopConfiguration = new HadoopConfiguration

    val hadoopStream = properties
      .toStream
      .filter { case (key, value) => startsWith(key, s"${prefix}") }

    hadoopStream
      .filter { case (key, value) => startsWith(key, s"${prefix}.files") }
      .map { case (key, value) => (substring(key, s"$prefix.files.".length), value) }
      .foreach{ case (key, value) => hadoopConfiguration.files.put(key, value) }

    val hadoopMap = hadoopStream
      .filter { case (key, value) => !startsWith(key, s"${prefix}.files") }
      .map { case (key, value) => (substring(key, s"$prefix.".length), value) }
      .toMap[String, Any]

    if (hadoopMap.contains("overwite")) {
      hadoopConfiguration.overwite = hadoopMap("overwite").asInstanceOf[Boolean]
    }

    val hadoopKerberos = hadoopMap
      .filter { case (key, value) => startsWith(key, "kerberos") }
      .map { case (key, value) => (substring(key, "kerberos.".length), value) }

    if (hadoopKerberos.nonEmpty) {
      hadoopConfiguration.kerberos = Kerberos(hadoopKerberos("principal").toString, hadoopKerberos("keytab").toString)
    }

    hadoopConfiguration
  }
}
