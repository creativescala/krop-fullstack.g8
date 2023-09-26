package $package$

import $package$.endpoints.Endpoints
import krop.all.*

object Main {
  val joke = Route(Request.get(Path.root / "joke"), Response.ok[String])

  val application =
    joke
      .handle(_ =>
        "Why did the chicken cross the road? To get to the other side!"
      )
      .otherwiseNotFound

  @main def run(): Unit =
    ServerBuilder.default
      .withApplication(application)
      .run()
}
