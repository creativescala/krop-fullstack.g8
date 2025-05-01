// give the user a nice default project!
ThisBuild / organization := "$organization$"
ThisBuild / scalaVersion := "3.6.4"
ThisBuild / semanticdbEnabled := true

// Dependency versions
val kropVersion = "$kropVersion$"
val munitVersion = "1.1.0"
val logbackVersion = "1.5.18"

// Run this command (build) to do everything involved in building the project
commands += Command.command("build") { state =>
  "clean" ::
    "compile" ::
    "test" ::
    "scalafixAll" ::
    "scalafmtAll" ::
    "scalafmtSbt" ::
    "dependencyUpdates" ::
    "reload plugins; dependencyUpdates; reload return" ::
    state
}

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(
    "org.creativescala" %% "krop-core" % kropVersion,
    "org.creativescala" %% "krop-sqlite" % kropVersion,
    "org.scalameta" %% "munit" % munitVersion % Test
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
    commonSettings,
    Compile / unmanagedSourceDirectories ++= Seq(
      baseDirectory.value.getParentFile / "src"
    )
  )
  .enablePlugins(KropLayout)

lazy val backend = project
  .in(file("backend"))
  .settings(
    name := """$name;format="normalize"$-backend""",
    commonSettings,
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime
    ),
    // This sets Krop into development mode, which gives useful tools for
    // developers. Krop runs in production mode if you don't set this.
    run / javaOptions += "-Dkrop.mode=development",
    reStart / javaOptions += "-Dkrop.mode=development",
    run / fork := true
  )
  .enablePlugins(KropLayout, KropTwirlLayout)
  .dependsOn(shared.jvm)

lazy val frontend = project
  .in(file("frontend"))
  .settings(
    name := """$name;format="normalize"$-frontend""",
    commonSettings
  )
  .enablePlugins(ScalaJSPlugin, KropLayout)
  .dependsOn(shared.js)

// This configures the welcome message you see when you start sbt. Change it to
// add tasks that are useful for your project.
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
  UsefulTask(
    "~backend/reStart",
    "Start the backend server, and restart on any change"
  ),
  UsefulTask("build", "Build everything from scratch"),
  UsefulTask("~compile", "Compile with file-watcher enabled")
)
