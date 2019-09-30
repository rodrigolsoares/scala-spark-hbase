package br.com.bigdata.spark

import java.util.Objects.isNull

import org.apache.hadoop.security.UserGroupInformation.{loginUserFromKeytab, setConfiguration}
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.slf4j.{Logger, LoggerFactory}

import scala.sys.props
import scala.util.{Failure, Success, Try}
import br.com.bigdata.config.{ApplicationConfig}

trait SparkExecutor extends  Serializable {

  val log: Logger = LoggerFactory.getLogger(classOf[SparkExecutor])

  implicit val spark: SparkSession = {

      SparkSession
        .builder()
        .appName("teste-spark")
        .config("spark.master", "local[4]")
        .config("spark.driver.allowMultipleContexts", true)
        .config("spark.sql.shuffle.partitions", 10)
        .enableHiveSupport()
        .getOrCreate()

  }

  implicit lazy val sc: SparkContext = {
    val sc: SparkContext = spark.sparkContext

    val applicationConfig = new ApplicationConfig();

    val hadoopConfiguration = applicationConfig.hadoopConfiguration

    if (hadoopConfiguration.overwite) {

      val configuration = HBaseConfiguration.create

      /*configuration.addResource(hadoopConfiguration.files("core-site.xml").toString, "core-site.xml")
      configuration.addResource(hadoopConfiguration.files("hbase-site.xml").toString, "hbase-site.xml")
      configuration.addResource(hadoopConfiguration.files("hdfs-site.xml").toString, "hdfs-site.xml")
      configuration.addResource(hadoopConfiguration.files("hive-site.xml").toString, "hive-site.xml")*/

      sc.hadoopConfiguration.addResource(configuration)

      if (hadoopConfiguration.isEnableKerberos) {
        setConfiguration(configuration)
        loginUserFromKeytab(hadoopConfiguration.kerberos.principal, hadoopConfiguration.kerberos.keytab)
      }
    }

    sc
  }



}
