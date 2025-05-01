package $package$

import cats.effect.IOApp
import krop.all.{*, given}
import $package$.conf.Context
import $package$.routes.Routes
import $package$.views.html

object Main extends IOApp.Simple {
  val name = "$name$"

  val home =
    Routes.home.handle(() => html.base(name, html.home(name)).toString)

  val assets =
    Routes.assets.passthrough

  val application =
    home.orElse(assets).orElse(Application.notFound)

  val run =
    Context.current.use { _ =>
      ServerBuilder.default
        .withApplication(application)
        .build
        .toIO
    }
}
