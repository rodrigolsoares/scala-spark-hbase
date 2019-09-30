package br.com.viavarejo.sync.streaming

import scala.util.{Failure, Try}
import java.util.{HashMap, Map}

import br.com.bigdata.config.ApplicationConfig




object StreamExecutor {

  def main(args: Array[String]): Unit = {

    println("Preparando para inicializar a aplicação")

    Try(init()) match {
      case Failure(exception) =>
        exception.printStackTrace()
        sys.exit(1)
      case _ =>
    }

  }

  def init(): Unit = {

    println("TESTE")


  }
}
