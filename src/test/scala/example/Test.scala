package example

import com.wp.hammal.actor.consumer.{ConsumerRouting, msg}
import com.wp.hammal.actor.init.Warm_up
import com.wp.hammal.actor.producer.{PorduceRun, ProducerRouting}
import com.wp.hammal.disruptor.{WP_disruptor, then}
import com.wp.hammal.plugins.reader.web.SpiderData
import com.wp.hammal.util.ConfigUtil
import scalaj.http.{Http, HttpOptions}
import com.wp.hammal.plugins.reader.web.SpiderConfig

/**
 * Created by yfwangpeng .
 */

object Test extends App with SpiderConfig{
  //启动disruptor
  val warm_up =   Warm_up !!!!! "sysactor"
  val props:Set[SpiderData] = getConfigProps("properties")
  val nums:Int = props.size
  //业务函数
  def mepa(spiderData:SpiderData):String={
    val options = List(HttpOptions.connTimeout(spiderData.connTimeout),HttpOptions.readTimeout(spiderData.readTimeout))
    var req = Http(spiderData.url).charset(spiderData.charset).options(options)
    val str = req.asString
    str
  }
  //启动actor pool,每个actor处理一个独立的业务
  props.foreach((spiderData)=> {
    var disruptor = WP_disruptor eating 2048 slots then work
    
    ConsumerRouting.roundRobinRouter ! msg(disruptor,spiderData)
    ProducerRouting.roundRobinRouter ! PorduceRun(Warm_up meth(mepa,spiderData),disruptor)
  })

}


