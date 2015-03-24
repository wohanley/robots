name := "robots"
scalaVersion := "2.11.6"

// specs2
libraryDependencies += "org.specs2" %% "specs2-core" % "3.0" % "test"
resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
scalacOptions in Test += "-Yrangepos"

// OpenNLP
libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "1.5.3"
