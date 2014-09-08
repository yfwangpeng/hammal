package com.wp.hammal.plugins.reader.web

import com.wp.hammal.util.ConfigUtil
import com.wp.hammal.util.ConfigException
import scala.collection.mutable.LinkedList

/**
 * Created by yfwangpeng .
 */
trait SpiderConfig extends ConfigUtil{
protected def getConfigProps(name: String):Set[SpiderData]={
	  val config = getConfig(name)
		val urls = config.get("urls", throw new ConfigException(s"Unable to create config $name, due to missing or invalid urls configuration"))
		val connTimeout = config.get("connTimeout", throw new ConfigException(s"Unable to create config $name, due to missing or invalid connTimeout configuration")).toInt
		val readTimeout = config.get("readTimeout", throw new ConfigException(s"Unable to create config $name, due to missing or invalid readTimeout configuration")).toInt
		val charset = config.get("charset", throw new ConfigException(s"Unable to create config $name, due to missing or invalid charset configuration"))
		val toStore = config.get("toStore", throw new ConfigException(s"Unable to create config $name, due to missing or invalid toStore configuration"))
		
		val list = LinkedList[SpiderData]()
		val arr : Array[String] = urls.split(",")
		var sps : Set[SpiderData] = Set[SpiderData]()
		for(url<-arr){
		  val sp = new SpiderData(url,connTimeout,readTimeout,charset,toStore)
		  	sps+=sp
		}
			sps
	}
}
