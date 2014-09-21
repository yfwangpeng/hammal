package example

import com.wp.hammal.actor.consumer.{ConsumerRouting}
import com.wp.hammal.actor.init.Warm_up
import com.wp.hammal.actor.producer.{PorduceRun, ProducerRouting}
import com.wp.hammal.disruptor.{WP_disruptor, then}
import com.wp.hammal.plugins.reader.web.SpiderData
import com.wp.hammal.util.ConfigUtil
import scalaj.http.{Http, HttpOptions}
import com.wp.hammal.plugins.reader.web.SpiderConfig
import com.wp.hammal.plugins.reader.web.SpiderReader
import com.wp.hammal.plugins.writer.hdfs.HdfsWriter
import com.wp.hammal.plugins.writer.hdfs.HdfsWriterFactory
import com.wp.hammal.disruptor.Disruptor1
import com.wp.hammal.actor.consumer.consumeRun
import scala.slick.driver.JdbcProfile
import com.wp.hammal.plugins.reader.mysql.SlickDBDriver
import com.wp.hammal.plugins.reader.mysql.MysqlReader


/**
 * Created by yfwangpeng .
 */

object Test extends App with SpiderConfig{
  //启动disruptor
  val warm_up =   Warm_up !!!!! "sysactor"
  val props:Set[SpiderData] = getConfigProps("properties")
  
  //启动actor pool,每个actor处理一个独立的业务
  props.foreach((spiderData)=> {
    var disruptor = WP_disruptor eating 2048 slots then work
    
    var content =  Warm_up meth(SpiderReader mepa,spiderData)
    def writer (implicit factory:HdfsWriterFactory)=factory.build(disruptor,spiderData)
    
    ConsumerRouting.roundRobinRouter ! consumeRun(writer)
    ProducerRouting.roundRobinRouter ! PorduceRun(content,disruptor)
  })
  

}


