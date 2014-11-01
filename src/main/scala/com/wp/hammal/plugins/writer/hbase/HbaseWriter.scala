package com.wp.hammal.plugins.writer.hbase

import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HColumnDescriptor, HTableDescriptor, HBaseConfiguration}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{Put, HTable, HBaseAdmin}

/**
 * Created by yfwangpeng on 2014/10/31.
 */
object HbaseWriter {
  var config:Configuration=null

  def save(props:HbaseData,value:String){
    config = HBaseConfiguration.create
    config.set("hbase.zookeeper.quorum", props.zkQuorum)
    config.set("hbase.zookeeper.property.clientPort",props.zkPort)
    config.set("zookeeper.znode.parent",props.zkParent)

    val tableName = props.table
    val rowKey = props.row
    val cfName = props.family
    val cqName = props.qualifier
    if(existsTable(tableName,config)){
      insertOneHbase(config,rowKey,tableName,cfName,cqName,value)
    }else{
      create(tableName,List(cfName),config)
      insertOneHbase(config,rowKey,tableName,cfName,cqName,value)
    }
  }

  def withHadmin(config: Configuration, tableName: String = null)(f: (HBaseAdmin, String) => Any) {
    val hAdmin = new HBaseAdmin(config)
    try {
      f(hAdmin, tableName)
    } finally {
      hAdmin.close()
    }
  }

  def create(tableName: String, families: List[String], config: Configuration) {
    withHadmin(config, tableName) {
      (hAdmin, tableName) =>
        val descriptor = new HTableDescriptor(tableName)
        for (family <- families)
          descriptor.addFamily(new HColumnDescriptor(Bytes.toBytes(family)))
        hAdmin.createTable(descriptor)
    }
  }

  def existsTable(tableName: String, config: Configuration): Boolean = {
    var isExists: Boolean = false
    withHadmin(config, tableName) {
      (hAdmin, tableName) =>
        isExists = hAdmin.tableExists(tableName)
    }
    isExists
  }

  def existsFamily(tableName: String, family:String,config: Configuration): Boolean = {
    var isExists:Boolean = false
    withHadmin(config,tableName){
      (hAdmin,tableName)=>
        if(existsTable(tableName,config)){
          val descriptor: HTableDescriptor = hAdmin.getTableDescriptor(Bytes.toBytes(tableName))
          isExists = descriptor.hasFamily(Bytes.toBytes(family))
        }
    }
    isExists
  }

  def withHtablePut(config: Configuration,rowKey: String,tableName: String,cfName: String,cqName: String,value: String)
                   (f: (HTable, String, String, String,String) => Unit) {
    val htable= new HTable(config,tableName)
    htable.setAutoFlush(false)
    try {
      f(htable, rowKey, cfName, cqName,value)
    } finally {
      htable.flushCommits()
      htable.close()
    }
  }

  def withHtablePuts(config: Configuration,rowKey: String,tableName: String,cfName: String,cqName: String,values: List[String])
                    (f: (HTable, String, String, String,List[String]) => Unit) {
    val htable= new HTable(config,tableName)
    htable.setAutoFlush(false)
    try {
      f(htable, rowKey, cfName, cqName,values)
    } finally {
      htable.flushCommits()
      htable.close()
    }
  }

  def insertOneHbase(config: Configuration,rowKey: String,tableName: String,cfName: String,cqName: String,value: String){
    withHtablePut(config,rowKey,tableName,cfName,cqName,value){
      (htable, rowKey, cfName, cqName,value) =>
        val put = new Put(Bytes.toBytes(rowKey))
        put.add(Bytes.toBytes(cfName), Bytes.toBytes(cqName), Bytes.toBytes(value))
        htable.put(put)
    }
  }

  def insertBatchHbase(config: Configuration,rowKey: String,tableName: String,cfName: String,cqName: String,values: List[String]){
    withHtablePuts(config,rowKey,tableName,cfName,cqName,values){
      (htable, rowKey, cfName, cqName,values)=>
        val putList = new java.util.ArrayList[Put]()
        values.foreach(
          (value:String)=>{
            val put = new Put(Bytes.toBytes(rowKey))
            put.add(Bytes.toBytes(cfName), Bytes.toBytes(cqName), Bytes.toBytes(value))
            putList.add(put)
          }
        )
        htable.put(putList)
    }
  }

  def insertBatchTimestampHbase(config: Configuration,rowKey: String,tableName: String,cfName: String,cqName: String,values: List[String]){
    withHtablePuts(config,rowKey,tableName,cfName,cqName,values){
      (htable, rowKey, cfName, cqName,values)=>
        val putList = new java.util.ArrayList[Put]()
        values.foreach(
          (value:String)=>{
            val arr:Array[String] = value.split(":")
            val put = new Put(Bytes.toBytes(rowKey+":"+arr(0)))
            put.add(Bytes.toBytes(cfName), Bytes.toBytes(cqName), arr(0).toLong,Bytes.toBytes(arr(1)))
            putList.add(put)
          }
        )
        htable.put(putList)
    }
  }
}
