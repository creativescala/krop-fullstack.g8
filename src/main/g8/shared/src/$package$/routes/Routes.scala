package $package$.routes

import krop.all.*
import krop.asset.AssetRoute

object Routes {
  val home =
    Route(
      Request.get(Path.root),
      Response.ok(Entity.html)
    )

  // This route serves static assets, such as Javascript or stylesheets, from
  // your resource directory.
  val assets =
    AssetRoute(Path / "assets", "resources/$package$/assets/")
}
