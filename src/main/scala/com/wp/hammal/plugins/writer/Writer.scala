package com.wp.hammal.plugins.writer

trait Writer{
    def apply(x:String): Unit
    def shutdown():Unit
  }