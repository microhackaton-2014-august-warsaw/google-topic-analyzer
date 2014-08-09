import scalaj.http.{Http, HttpOptions}

organization := "com.ofg"

name := """google-topic-analyzer"""

version := "1.0"

publishMavenStyle := true

credentials += Credentials("Sonatype Nexus Repository Manager", "nexus.microhackathon.pl", "deployment", "deployment123")

publishTo := Some("Microhackathon repository" at "http://nexus.microhackathon.pl/content/repositories/releases/")

val port = 8905

val deployTask = TaskKey[Unit]("deployTask",
  "Deletes files produced by the build, such as generated sources, compiled classes, and task caches.")

deployTask := {
  val json = s"""{"artifactId": "$name", "groupId": "$organization","version": "$version",
                     "jvmParams": "-Dhttp.port=$port
                      -Dservice.resolver.url=zookeeper.microhackathon.pl:2181"}"""
  println(s"Sending the following json [$json]")
  println(Http.postData("http://54.73.40.79:18081/deploy", json)
    .header("Content-Type", "application/json")
    .header("Charset", "UTF-8")
    .responseCode)
  //   IO.htt
  //new groovyx.net.http.HTTPBuilder('http://54.73.40.79:18081/deploy').post([body: json, headers: ['Content-Type': 'application/json']])
}

libraryDependencies ++= Seq(
  "com.ofg" % "micro-deps" % "0.5.0",
  "com.wordnik" % "swagger-play2_2.10" % "1.3.7",
  "com.google.guava" % "guava" % "14.0",
  "com.google.inject" % "guice" % "3.0",
  "javax.inject" % "javax.inject" % "1",
  "org.scala-lang" % "scala-library" % "2.10.4"
)

play.Project.playScalaSettings