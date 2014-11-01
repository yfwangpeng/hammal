package demo

import com.wp.hammal.actor.consumer.{ConsumerRouting, consumeRun}
import com.wp.hammal.actor.init.Warm_up
import com.wp.hammal.actor.producer.{PorduceRun, ProducerRouting}
import com.wp.hammal.disruptor.{WP_disruptor, then}
import com.wp.hammal.plugins.reader.web.{SpiderConfig, SpiderData, SpiderReader}
import com.wp.hammal.plugins.writer.hdfs.HdfsWriterFactory


/**
 * Created by yfwangpeng .
 */

object SpiderToHadoopDemo extends App with SpiderConfig{
  //启动disruptor
  val warm_up =   Warm_up !!!!! "sysactor"
  val props:Set[SpiderData] = getConfigProps()
  
  //启动actor pool,每个actor处理一个独立的业务
  props.foreach((spiderData)=> {
    var disruptor = WP_disruptor eating 2048 slots then work
    
    var content =  Warm_up meth(SpiderReader mepa,spiderData)
    def writer (implicit factory:HdfsWriterFactory)=factory.build(disruptor,spiderData)
    
    ConsumerRouting.roundRobinRouter ! consumeRun(writer)
    ProducerRouting.roundRobinRouter ! PorduceRun(content,disruptor)
  })
}


