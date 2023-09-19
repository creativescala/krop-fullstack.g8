// give the user a nice default project!
ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / semanticdbEnabled := true

// Run this command (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "dependencyUpdates" ::
    "compile" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    state
}

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.creativescala" %% "krop" % "0.1",
    "org.scalameta" %% "munit" % "0.7.29" % "test"
  )
)

lazy val root = project
  .in(file("."))
  .settings(
    name := """$name;format="normalize"$"""
  )
  .aggregate(backend, frontend, shared)

lazy val shared = crossproject(JSPlatform, JVMPlatform)
  .in(file("shared"))
  .settings(
    name := """$name;format="normalize"$-shared""",
    commonSettings
  )
  .crossType(CrossType.Pure)

lazy val backend = project
  .in(file("backend"))
  .settings(
    name := """$name;format="normalize"$-backend""",
    commonSettings
  )
  .dependsOn(shared)

lazy val frontend = project
  .in(file("frontend"))
  .settings(
    name := """$name;format="normalize"$-frontend""",
    commonSettings
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared)
