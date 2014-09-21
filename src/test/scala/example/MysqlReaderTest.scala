package example

import com.wp.hammal.plugins.reader.mysql.MysqlReader
import com.wp.hammal.disruptor.WP_disruptor
import com.wp.hammal.actor.init.Warm_up
import com.wp.hammal.disruptor.`then`
import com.wp.hammal.actor.producer.ProducerRouting
import com.wp.hammal.actor.producer.PorduceRun

object MysqlReaderTest extends App{
 val sql:String = "select id,name,email from employee"
  val list = new MysqlReader("mysql",sql).getData
    val disruptor = WP_disruptor eating 2048 slots then work
    
    list foreach (r=>{
      val content = r._1+"\t"+r._2+"\t"+r._3+"\n"
      println(content)
      ProducerRouting.roundRobinRouter ! PorduceRun(content,disruptor)
}
  )
}