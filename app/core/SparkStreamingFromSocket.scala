package core


import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{explode, split}

object SparkStreamingFromSocket {

  def run(): Unit = {
    println("Starting spark streaming")

    val spark: SparkSession = SparkSession.builder()
      .master("spark://192.168.10.4:7077")
      .appName("SparkByExample")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    println("Query: ")
    println("End...")
  }
}


