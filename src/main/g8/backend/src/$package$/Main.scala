package $package$

import cats.effect.ExitCode
import cats.effect.IO
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp
import krop.all.{*, given}
import krop.tool.cli.*
import krop.BuildInfo

import $package$.conf.Context
import $package$.routes.Routes
import $package$.views.html

val name = "$name$"

object Main
    extends CommandIOApp(
      name = name,
      header = "An amazing web application built with Krop"
    ) {

  val home =
    Routes.home.handle(() =>
      html.base(name, html.home(name, BuildInfo.kropVersion)).toString
    )

  val assets =
    Routes.assets

  val application =
    home.orElse(assets).orElse(Application.notFound)

  override def main: Opts[IO[ExitCode]] =
    (Cli.serveOpts.orElse(Cli.migrateOpts)).map {
      case Serve(port) =>
        Context.current.use { _ =>
          ServerBuilder.default
            .withApplication(application)
            .withPort(port)
            .build
            .toIO
            .as(ExitCode.Success)
        }

      case Migrate() => ???
    }
}
