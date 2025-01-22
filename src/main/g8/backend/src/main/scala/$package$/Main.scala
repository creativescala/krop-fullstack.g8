package $package$

import $package$.html
import $package$.routes.Routes
import krop.all.{*, given}

object Main {
  val name = "$name$"

  val index = Route(
    Request.get(Path.root),
    Response.ok(Entity.html)
  ).(() => html.index(name))

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
