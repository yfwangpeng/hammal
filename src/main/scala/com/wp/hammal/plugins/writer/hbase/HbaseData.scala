package com.wp.hammal.plugins.writer.hbase

/**
 * Created by yfwangpeng.
 */


class HbaseData(val zkQuorum:String,val zkPort : String,val zkParent:String,
                val table:String,val row:String,val family:String,val qualifier:String) {
  private val hzkQuorum=zkQuorum
  private val hzkPort=zkPort
  private val hzkParent=zkParent

  private val htable = ""
  private val hrow = ""
  private val hfamily = ""
  private val hqualifier = ""
}

