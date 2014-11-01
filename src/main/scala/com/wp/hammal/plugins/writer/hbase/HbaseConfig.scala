package com.wp.hammal.plugins.writer.hbase

import com.wp.hammal.plugins.reader.web.SpiderData
import com.wp.hammal.util.{ConfigException, ConfigUtil}

import scala.collection.mutable

/**
 * Created by yfwangpeng.
 */
trait HbaseConfig extends ConfigUtil {
  protected def getConfigProps(): HbaseData = {
    val configRoot = "hbase"
    val config = getConfig(configRoot, "")
    val zkQuorum = config.get("zkQuorum", throw new ConfigException(s"Unable to create config , due to missing or invalid zkQuorum configuration"))
    val zkPort = config.get("zkPort", throw new ConfigException(s"Unable to create config , due to missing or invalid zkPort configuration"))
    val zkParent = config.get("zkParent", throw new ConfigException(s"Unable to create config , due to missing or invalid zkParent configuration"))

    val table : String =""
    val row : String = ""
    val family : String = ""
    val qualifier : String = ""

    val configData = new HbaseData(zkQuorum, zkPort, zkParent,table,row,family,qualifier)
    configData
  }
}
