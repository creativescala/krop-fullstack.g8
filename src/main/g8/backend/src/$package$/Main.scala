package $package$

import $package$.routes.Routes
import $package$.views.html
import krop.all.{*, given}

object Main {
  val name = "$name$"

  val home =
    Routes.home.handle(() => html.base(name, html.home(name)).toString)

  val assets =
    Routes.assets.passthrough

  val application =
    home.orElse(assets).orElse(Application.notFound)

  @main def run(): Unit =
    ServerBuilder.default
      .withApplication(application)
      .run()
}
