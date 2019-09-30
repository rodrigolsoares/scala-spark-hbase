package br.com.viavarejo.sync.streaming.infrastructure.config

import scala.beans.BeanProperty

class Kerberos  extends Serializable {

  @BeanProperty
  var principal: String = _

  @BeanProperty
  var keytab: String = _

  override def toString = s"Kerberos(principal: $principal, keytab: $keytab)"

}
object Kerberos  extends Serializable {
  def apply(principal: String, keytab: String): Kerberos = {
    val kerberos = new Kerberos()
    kerberos.principal = principal
    kerberos.keytab = keytab
    kerberos
  }
}