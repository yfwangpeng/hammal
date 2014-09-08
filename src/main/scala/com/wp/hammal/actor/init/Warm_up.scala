package com.wp.hammal.actor.init

import akka.actor.ActorSystem

/**
 * Created by yfwangpeng .
 */
object Warm_up {

  def !!!!! (name:String):ActorSystem=ActorSystem(name)

  def meth[T](fx:T=>String,x:T):String={
    fx(x)
  }
}



