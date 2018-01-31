enablePlugins(ScalaJSPlugin)

name := "Scala.js Tutorial"
scalaVersion := "2.12.2" // or any other Scala version >= 2.10.2

// scalacOptions += "-Xxml:coalescing" //support cdata style

// This is an application with a main method
scalaJSUseMainModuleInitializer := true

libraryDependencies += "com.thoughtworks.binding" %%% "dom" % "latest.release"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
