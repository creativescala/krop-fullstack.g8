package $package$

import $package$.routes.Routes
import $package$.views.html
import krop.all.{*, given}

object Main {
  val name = "$name$"

  val index = Route(
    Request.get(Path.root),
    Response.ok(Entity.html)
  ).handle(() => html.base(name, html.index(name).toString).toString)

  val joke = Routes.joke
    .handle(() =>
      "Why did the chicken cross the road? To get to the other side!"
    )

  val application =
    index.orElse(joke).orElse(Application.notFound)

  @main def run(): Unit =
    ServerBuilder.default
      .withApplication(application)
      .run()
}
