package com.wp.hammal.plugins.reader.web

import scalaj.http.HttpOptions
import scalaj.http.Http

object SpiderReader {

  def mepa(spiderData:SpiderData):String={
    val options = List(HttpOptions.connTimeout(spiderData.connTimeout),HttpOptions.readTimeout(spiderData.readTimeout))
    var req = Http(spiderData.url).charset(spiderData.charset).options(options)
    val str = req.asString
    str
  }
}