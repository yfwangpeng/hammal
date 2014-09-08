package com.wp.hammal.disruptor

import java.util.concurrent.{Executor, Executors}

/**
 * Created by yfwangpeng .
 */


object WP_disruptor extends DisruptorTraitor{
  protected def config = DisruptorConfiguration()
}

case object then

case class DisruptorConfiguration private[disruptor](bufferSize: Int = 1024, executor: Option[Executor] = None) extends DisruptorTraitor
{
  protected def config:DisruptorConfiguration = this
}

case class EatingBufferSize private[disruptor] (copy: DisruptorConfiguration){
  def slots(in: then.type): DisruptorConfiguration = copy
}

trait DisruptorTraitor{
  protected def config: DisruptorConfiguration
  private def realExecutor = config.executor.getOrElse(Executors.newCachedThreadPool())
  def eating(bufferSize: Int):EatingBufferSize = EatingBufferSize(config.copy(bufferSize = config.bufferSize))
  def work (implicit factory: DisruptorFactory1) =factory.build(config.bufferSize, realExecutor)
}




