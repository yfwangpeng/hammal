package com.wp.hammal.util

import com.wp.hammal.plugins.reader.web.SpiderData

import scala.collection.mutable.LinkedList

trait ConfigUtil extends Config {

   def getConfig(configRoot: String,app:String)=poolConfig(configRoot,app)

}