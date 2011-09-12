organization := "com.novoda"

name := "github"

version := "0.1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "net.databinder" %% "dispatch-http" % "0.8.5",
  "net.databinder" %% "dispatch-lift-json" % "0.8.5",
  "net.databinder" %% "dispatch-nio" % "0.8.5",
  "org.eclipse.jgit" % "org.eclipse.jgit" % "1.0.0.201106090707-r",
  "com.github.scopt" %% "scopt" % "1.1.2"
)

resolvers += "Scala Tools Nexus Releases" at "http://nexus.scala-tools.org/content/repositories/releases/"

resolvers += "jgit-repo" at "http://download.eclipse.org/jgit/maven"
