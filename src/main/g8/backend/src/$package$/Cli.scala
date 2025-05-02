package $package$

import cats.syntax.all.*
import com.monovore.decline.*
import com.comcast.ip4s.Port
import krop.all.port

// The top-level commands the CLI parses
final case class Serve(port: Port)
final case class Migrate()

// Define command line parsing options
object Cli {
  val serveOpts: Opts[Serve] =
    Opts.subcommand("serve", "Serve the web application.") {
      Opts
        .option[Int]("port", "The port to use. Defaults to 8080.", short = "p")
        .orNone
        .map(opt => opt.flatMap(Port.fromInt))
        .map(port => Serve(port.getOrElse(port"8080")))
    }

  val migrateOpts: Opts[Migrate] =
    Opts
      .subcommand("migrate", "Run the database migrations.") {
        Opts.unit
      }
      .as(Migrate())
}
