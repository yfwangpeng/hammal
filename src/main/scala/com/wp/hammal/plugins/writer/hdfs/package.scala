package com.wp.hammal.plugins.writer

import com.wp.hammal.disruptor.Disruptor1
import com.wp.hammal.plugins.reader.web.SpiderData
import com.wp.hammal.plugins.writer.Writer

package hdfs{
  trait HdfsWriterFactory{
	  def build(y:Disruptor1,z:SpiderData):Writer
}
}

package object hdfs {
  implicit def hdfsWriterBuilder:HdfsWriterFactory = {
    
    new HdfsWriterFactory{
      def build(y:Disruptor1,z:SpiderData):Writer={
        
        class WriterImp(y:Disruptor1) extends Writer{
          
          def apply(x:String):Unit={
            y { a =>
            	val str = s"a: $a"
            	println(str)
//            	HdfsWriter.saveFile(z, str)
            }
            y.start()
          }
          
          def shutdown()=y.shutdown
        }
        
        new WriterImp(y)
      }
    }
    
  }
}