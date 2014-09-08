package com.wp.hammal.plugins.writer.hdfs
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FSDataOutputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import java.io.File
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import org.apache.hadoop.fs.FileStatus
import com.wp.hammal.plugins.reader.web.SpiderData
import java.net.URL

object HdfsWriter {
  private val conf = new Configuration()
  private val hdfsCoreSitePath = new Path("core-site.xml")
  private val hdfsHDFSSitePath = new Path("hdfs-site.xml")
  conf.addResource(hdfsCoreSitePath)
  conf.addResource(hdfsHDFSSitePath)
  private val fileSystem = FileSystem.get(conf)
  
  def saveFile(spiderData:SpiderData,content:String): Unit = {
    val u = spiderData.url
    val netUrl = new URL(u)
    val out = fileSystem.create(new Path(spiderData.toStore+netUrl.getHost()))
    out.writeBytes(content)
    out.close()
	fileSystem.listStatus(new Path(spiderData.toStore+netUrl.getHost())).foreach((file)=>println(file.getPath()))
  }

}