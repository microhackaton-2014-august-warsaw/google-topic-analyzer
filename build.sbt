name := """google-topic-analyzer"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.ofg" % "micro-deps" % "0.5.0",
  "com.wordnik" % "swagger-play2_2.10" % "1.3.7",
  "com.google.guava" % "guava" % "14.0",
  "com.google.inject" % "guice" % "3.0",
  "javax.inject" % "javax.inject" % "1",
  "org.scala-lang" % "scala-library" % "2.10.4"
)

play.Project.playScalaSettings