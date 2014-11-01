package com.wp.hammal.plugins.writer

import com.wp.hammal.disruptor.Disruptor1

/**
 * Created by yfwangpeng on 2014/10/31.
 */

package hbase{
    trait HbaseWriterFactory{
      def build(y:Disruptor1,props:HbaseData):Writer
    }
}

package object hbase {
      implicit def factory:HbaseWriterFactory = {
        new HbaseWriterFactory{
            def build(y:Disruptor1,props:HbaseData):Writer={
              class WriterImp(y:Disruptor1) extends Writer{
                  def apply(x:String):Unit={
                      y{
                        content=>println(content)
                        HbaseWriter.save(props,content)
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
