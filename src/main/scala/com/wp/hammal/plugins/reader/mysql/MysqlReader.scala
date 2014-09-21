package com.wp.hammal.plugins.reader.mysql

import scala.slick.driver.JdbcProfile
import scala.slick.driver.JdbcDriver.backend.Database
import Database.dynamicSession
import scala.slick.jdbc.{GetResult, StaticQuery => Q}

class MysqlReader(dbType:String,sql:String)  extends Profile {
  override val profile: JdbcProfile = SlickDBDriver.getDriver(dbType)
  import profile.simple._
  val conn = new DBConnection(profile)
  def getData: List[(String,String,String)] = {
     val query = Q.query[Int,(String,String,String)](sql+" where 1= ?")
     conn.dbObject.withDynSession{ 
     query(1).list
   }
  }
}