
import com.github.nikdon.telepooz.engine.{ApiRequestExecutor, CommandBasedReactions, Polling, Reactor, Telepooz}

object NaiveBot extends Telepooz with App {

  implicit val are: ApiRequestExecutor = new ApiRequestExecutor {}
  val poller: Polling = new Polling
  val reactor: Reactor = new Reactor {
  val reactions: CommandBasedReactions = CommandBasedReactions()
      .on("/start")(implicit message ⇒ args ⇒ reply("You are started!"))
      .on("/test")(implicit message ⇒ args ⇒ reply("Hello world!"))
  }

  instance.run((are, poller, reactor))
}