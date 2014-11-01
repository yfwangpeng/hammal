package demo

import com.wp.hammal.actor.consumer.{consumeRun, ConsumerRouting}
import com.wp.hammal.actor.init.Warm_up
import com.wp.hammal.actor.producer.{PorduceRun, ProducerRouting}
import com.wp.hammal.disruptor.{then, WP_disruptor}
import com.wp.hammal.plugins.reader.mysql.MysqlReader
import com.wp.hammal.plugins.reader.web.{SpiderConfig, SpiderData}
import com.wp.hammal.plugins.writer.hbase.{HbaseWriter, HbaseConfig, HbaseData, HbaseWriterFactory}

/**
 * Created by yfwangpeng .
 */
object MysqlToHbaseDemo extends App  with HbaseConfig{
  //启动disruptor
  val warm_up =   Warm_up !!!!! "sysactor"
  val props:HbaseData = getConfigProps()
  var disruptor = WP_disruptor eating 2048 slots then work

  def writer (implicit factory:HbaseWriterFactory) = factory.build(disruptor,props)
  ConsumerRouting.roundRobinRouter ! consumeRun(writer)

  val sql:String = "select id,name,email from employee"
  val list = new MysqlReader("mysql",sql).getData
  list foreach (r=>{
    val content = r._1+"\t"+r._2+"\t"+r._3+"\n"
    ProducerRouting.roundRobinRouter ! PorduceRun(content,disruptor)
  })

}
