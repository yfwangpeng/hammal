package com.wp.hammal.actor.consumer
import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinRouter

object ConsumerRouting {
 val roundRobinRouter =
    ActorSystem("DODOMO").actorOf(Props[ConsumerActor].withRouter(RoundRobinRouter(5)), "router")
}