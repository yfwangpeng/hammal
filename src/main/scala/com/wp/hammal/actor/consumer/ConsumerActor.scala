package com.wp.hammal.actor.consumer

import akka.actor.Actor
import com.wp.hammal.disruptor.Disruptor1
import com.wp.hammal.plugins.writer.hdfs.HdfsWriter
import com.wp.hammal.plugins.reader.web.SpiderData
import com.wp.hammal.plugins.writer.Writer

case class consumeRun(writer:Writer)
class ConsumerActor extends Actor{
def receive = {
case consumeRun(writer)=>
	writer("ok")
	writer.shutdown
	context.stop(self)
}

}