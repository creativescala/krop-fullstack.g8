package $package$

import $package$.endpoints
import krop.all.*

object Main extends Endpoints, KropEndpoints {

  val application =
    joke
      .handle(_ =>
        "Why did the chicken cross the road? To get to the other side!"
      )
      .otherwiseNotFound

  @main def go(): Unit =
    ServerBuilder.withApplication(application)
}
