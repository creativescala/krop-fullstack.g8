package $package$.endpoints

trait Endpoints extends endpoints4s.algebra.Endpoints {
  val joke = endpoint(get(path / "joke"), ok(textResponse))
}
