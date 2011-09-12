libraryDependencies <+= (sbtVersion) { sv =>
  "net.databinder" %% "conscript-plugin" % ("0.3.1_sbt" + sv)
}

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

libraryDependencies += "com.github.mpeltonen" %% "sbt-idea" % "0.10.0"