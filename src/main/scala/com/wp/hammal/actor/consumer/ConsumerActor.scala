package com.wp.hammal.actor.consumer

import akka.actor.Actor
import com.wp.hammal.disruptor.Disruptor1
import com.wp.hammal.plugins.writer.hdfs.HdfsWriter
import com.wp.hammal.plugins.reader.web.SpiderData

case class msg(y:Disruptor1,z:SpiderData)
class ConsumerActor extends Actor{
def receive = {
case msg(y,z) ⇒
      println("starting writing '%s' in actor %s".format(msg, self.path.name))
      println("consuming data from ringbuffer")
      y { a =>
          println(s"a: $a")
          HdfsWriter.saveFile(z,a)
        }
        y.start()
        context.stop(self)
}
}