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


    val df = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", "9090")
      .load()

    val wordsDF = df.select(explode(split(df("value"), " ")).alias("word"))
    val count = wordsDF.groupBy("word").count()
    val query = count.writeStream
      .format("console")
      .outputMode("complete")
      .start()
      .awaitTermination()

    println("Query: " + query.toString)
    println("End...")
  }
}


