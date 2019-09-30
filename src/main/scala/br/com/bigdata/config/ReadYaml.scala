package br.com.bigdata.config

import java.util.Map
import org.yaml.snakeyaml.Yaml



object ReadYaml {

    def main(args: Array[String]): Unit = {

        val yaml = new Yaml();
        val fileStream = getClass.getResourceAsStream("/scala-spark-hbase.yml")

        val yamlMaps: Map[String, Object] = yaml.load(fileStream)
        val hadoopConfiguration: Map[String, Object] = yamlMaps.get("hadoopConfiguration").asInstanceOf[Map[String, Object]]
        val files: Map[String, Object] = hadoopConfiguration.get("files").asInstanceOf[Map[String, Object]]

        import scala.collection.JavaConversions._
        for (key <- files.keySet) {
            println(s" Chave: $key  valor: $files.get(key)")
        }


    }



}
