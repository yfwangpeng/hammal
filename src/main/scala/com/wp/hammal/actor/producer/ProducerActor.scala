package com.wp.hammal.actor.producer

import akka.actor.Actor
import com.wp.hammal.disruptor.Disruptor1

case class PorduceRun(x:String,y:Disruptor1)

class ProducerActor extends Actor {
def receive = {
case PorduceRun(x,y) ⇒
  y("Text: " + x)
  context.stop(self)
}
}
