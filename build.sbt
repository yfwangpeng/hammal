name := "hammal"

version := "1.1"
 
libraryDependencies++= Seq(
  //"org.apache.hadoop" % "hadoop-client" % "1.2.1",
  "com.lmax" % "disruptor" % "3.2.1",
  "org.specs2" %% "specs2" % "2.3.12",
  "com.typesafe.akka" % "akka-actor_2.10" % "2.2.3",
  "com.typesafe" % "config" % "1.0.0",
  "org.scala-lang" % "scala-reflect" % "2.10.3",
  "org.scalaj"%"scalaj-http_2.10"%"0.3.16",
  "org.scalatest" % "scalatest_2.10" % "1.9.2",
  "com.typesafe.slick" %% "slick" % "2.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "mysql" % "mysql-connector-java" % "5.1.32",
    //hbase
  "org.apache.hbase" % "hbase" % "0.94.18",
  "org.apache.hadoop" % "hadoop-client" % "2.2.0"
)
    
