// give the user a nice default project!
ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / semanticdbEnabled := true

val kropVersion = "0.2"

// Run this command (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "dependencyUpdates" ::
    "clean" ::
    "compile" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    state
}

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.endpoints4s" %%% "algebra" % "1.10.0",
    "org.scalameta" %% "munit" % "0.7.29" % "test"
  )
)

lazy val root = project
  .in(file("."))
  .settings(
    name := """$name;format="normalize"$"""
  )
  .aggregate(backend, frontend, shared.jvm, shared.js)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := """$name;format="normalize"$-shared""",
    commonSettings
  )

lazy val backend = project
  .in(file("backend"))
  .settings(
    name := """$name;format="normalize"$-backend""",
    commonSettings,
    libraryDependencies ++= Seq(
      "org.creativescala" %% "krop-core" % kropVersion,
      "ch.qos.logback" % "logback-classic" % "1.4.11" % Runtime
    ),
    run / javaOptions += "-Dkrop.mode=development",
    run / fork := true
  )
  .dependsOn(shared.jvm)

lazy val frontend = project
  .in(file("frontend"))
  .settings(
    name := """$name;format="normalize"$-frontend""",
    commonSettings
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(shared.js)

// This configures the welcome message you see when you start sbt

import sbtwelcome._

logo :=
  raw"""
     |Welcome to
     |
     | _     _  ______  _____   _____
     | |____/  |_____/ |     | |_____]
     | |    \_ |    \_ |_____| |
     |
     |Version \${kropVersion}
   """.stripMargin

usefulTasks := Seq(
  UsefulTask("backend/run", "Start the backend server"),
  UsefulTask("~compile", "Compile with file-watcher enabled"),
  UsefulTask("build", "Build everything from scratch")
)
