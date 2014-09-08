package com.wp.hammal

import java.util.concurrent.Executor

import com.lmax.disruptor.dsl.{Disruptor => LDisruptor}
import com.lmax.disruptor.{EventFactory, EventHandler, RingBuffer}

/**
 * Created by yfwangpeng .
 */


package disruptor{

trait BaseDisruptor {
  def start(): Unit
  def shutdown: Unit
}

trait Disruptor1 extends BaseDisruptor{
  def apply(f: String => Unit): Unit
  def apply(a: String): Unit
}

trait DisruptorFactory1 {
  def build(bufferSize: Int, realExecutor: Executor): Disruptor1
}
}

package object disruptor {
  implicit def factoryBuilder1: DisruptorFactory1= factoryBuilder1Impl
  def factoryBuilder1Impl: DisruptorFactory1 = {

    new DisruptorFactory1 {

      def build(bufferSize: Int, realExecutor: Executor): Disruptor1 = {

        class DisruptorEvent1() {
          var a: String = _
        }

        class DisruptorEvent1Factory extends EventFactory[DisruptorEvent1] {
          def newInstance: DisruptorEvent1 = new DisruptorEvent1()
        }

        val ldisruptor = new LDisruptor[DisruptorEvent1](new DisruptorEvent1Factory(), bufferSize, realExecutor)
        val ringBuffer = ldisruptor.getRingBuffer()

        class Disruptor1Impl(ldisruptor: LDisruptor[DisruptorEvent1], ringBuffer: RingBuffer[DisruptorEvent1]) extends Disruptor1 {

          def apply(f: String => Unit): Unit = {
            ldisruptor.handleEventsWith(new EventHandler[DisruptorEvent1]() {
              override def onEvent(event: DisruptorEvent1, sequence: Long, endOfBatch: Boolean) =
                f(event.a)
            })
          }

          def apply(a: String): Unit = {
            val sequence = ringBuffer.next() // Grab the next sequence
            try {
              val event = ringBuffer.get(sequence) // Get the entry in the Disruptor
              // for the sequence
              event.a = a
            }
            finally {
              ringBuffer.publish(sequence)
            }
          }
          def start() = ldisruptor.start()
          def shutdown() = ldisruptor.shutdown()
        }
        new Disruptor1Impl(ldisruptor, ringBuffer)
      }
    }
  }
}