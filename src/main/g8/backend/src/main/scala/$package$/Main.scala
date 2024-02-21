package $package$

import krop.all.{*, given}
import $package$.routes.Routes

object Main {
  val index = Route(
    Request.get(Path.root),
    Response.staticFile("assets/index.html")
  ).passthrough

  val joke = Route(Request.get(Path / "joke"), Response.ok(Entity.text))
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
