package com.wp.hammal.actor.producer

import akka.actor.{ActorSystem, Props}
import akka.routing.RoundRobinRouter
import com.wp.hammal.util.ConfigUtil

object ProducerRouting extends ConfigUtil{

 val roundRobinRouter = ActorSystem("DODOMO").actorOf(Props[ProducerActor].withRouter(RoundRobinRouter(5)), "router")

}


