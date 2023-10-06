package $package$

import $package$.endpoints.Endpoints
import krop.all.*

object Main {
  val index = Route(
    Request.get(Path.root),
    Response.staticFile("assets/index.html")
  ).passthrough

  val joke = Route(Request.get(Path.root / "joke"), Response.ok[String])
    .handle(() =>
      "Why did the chicken cross the road? To get to the other side!"
    )

  val application =
    index.orElse(joke).otherwiseNotFound

  @main def run(): Unit =
    ServerBuilder.default
      .withApplication(application)
      .run()
}
