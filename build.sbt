name := """play-spark-jobs"""
organization := "pt.mleiria"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.12"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.0",
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.spark" %% "spark-mllib" % "3.0.0",
  "org.apache.spark" %% "spark-streaming" % "3.0.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "pt.mleiria.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "pt.mleiria.binders._"
