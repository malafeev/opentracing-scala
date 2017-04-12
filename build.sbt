
lazy val commonSettings = Seq(
  organization := "io.opentracing",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.1",
  libraryDependencies ++= commonDeps
)

lazy val root = (project in file("."))
  .aggregate(api, impl, mock, noop)
  .settings(
    commonSettings,
    name := "OpenTracing Scala"
  )


lazy val api = (project in file("api"))
  .settings(
    commonSettings
  )

lazy val impl = (project in file("impl"))
  .settings(
    commonSettings
  )
  .dependsOn(api, noop)

lazy val mock = (project in file("mock"))
  .settings(
    commonSettings
  )
  .dependsOn(api)

lazy val noop = (project in file("noop"))
  .settings(
    commonSettings
  )
  .dependsOn(api)

lazy val commonDeps = Seq(
  "org.scalactic" %% "scalactic" % "3.0.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.scalamock" %% "scalamock-scalatest-support" % "3.5.0" % Test,
  "org.mockito" % "mockito-core" % "2.7.21" % "test"
)
