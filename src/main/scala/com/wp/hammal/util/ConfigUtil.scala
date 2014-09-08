package com.wp.hammal.util

import com.wp.hammal.plugins.reader.web.SpiderData

import scala.collection.mutable.LinkedList

trait ConfigUtil extends Config {

   def getConfig(name:String)=poolConfig(name)

}

