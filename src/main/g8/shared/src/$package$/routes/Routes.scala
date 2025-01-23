package $package$.routes

import krop.all.*

object Routes {
  val joke = Route(Request.get(Path / "joke"), Response.ok(Entity.text))
}
