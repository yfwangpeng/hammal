hammal
======

###背景
互联网公司的数据部门每天会面临大量数据的导入导出，它们来自不同类型的数据源，去往不同的目的地，它们数据量大的惊人，数据交换会带来额外的开销，执行效率差别很大。
因此，我们需要这样的数据交换平台，1，满足异构数据的导入导出；2，数据交换的性能开销越低越好；3，数据传输过程避免过多的磁盘IO操作，实现全内存；4，良好的开放API，
采用framework_plugin构建，业务方个性化plugin.
基于这样的思想，京东开发了plumber系统，taobao推出了DataX系统，前者基于clojure，后者基于java开发，hammal 则采用了scala，通过基于scala的ringbuffer,akka构建一个
异构数据快速交换系统。

![](https://github.com/yfwangpeng/hammal/blob/master/img/hammal.png)
<br/>
<br/>
![](https://github.com/yfwangpeng/hammal/blob/master/img/data flow.png)
<br/>
<p>data flow from first tier hammals to the second tier hammals. the writer in the first tier point to the reader in the second tier.</p>
<p>the reader in the second tier hammals consolidates the received message into a file or memory channel which is consumed by a writer to its final data destinations  </p>

###关于项目
#####运行环境
		jdk1.7
		scala 2.10.2

#####编译及打包
		cd hammal
		sbt
		clean
		compile
		package

#####例子
MysqlReaderDemo.scala<br/>
MysqlToHbaseDemo.scala<br/>
SpiderToHadoopDemo.scala<br/>

###roadmap
Commits on Sep 8, 2014 <br/>
framework and webspider reader plugin, hdfs writer plugin <br/>
Commits on Sep 21, 2014 <br/>
mysql reader plugin<br/>
Commits on Nov 1, 2014 <br/>
hbase writer plugin, data flow model <br/>

