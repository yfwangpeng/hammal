package com.wp.hammal.plugins.reader.web

class SpiderData
(val url : String,val connTimeout : Int,val readTimeout : Int,val charset : String,val toStore : String)
{
  private val surl=url
  private val sconnTimeout =connTimeout
  private val sreadTimeout = readTimeout
  private val scharset = charset
  private val stoStore = toStore
}
