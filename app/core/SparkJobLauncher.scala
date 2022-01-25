package core

import sys.process._

object SparkJobLauncher {

  val scriptFile = "/opt/glider/ml-spark-jobs/./ml-spark-jobs.sh"



  def run() = {
    scriptFile !
  }
}
