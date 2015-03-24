name := "robots"
organization := "com.wohanley"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.11.6"
publishMavenStyle := true

// specs2
libraryDependencies += "org.specs2" %% "specs2-core" % "3.0" % "test"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
scalacOptions in Test += "-Yrangepos"

// OpenNLP
libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.5.3"

// parser combinators
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"

// twitter4j
libraryDependencies +=  "org.twitter4j" % "twitter4j-stream" % "4.0.2"
