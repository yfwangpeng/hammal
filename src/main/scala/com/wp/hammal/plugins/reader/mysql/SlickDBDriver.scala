package com.wp.hammal.plugins.reader.mysql

import scala.slick.driver.JdbcProfile
import scala.slick.driver.H2Driver
import scala.slick.driver.MySQLDriver
 
object SlickDBDriver {
val H2 = "h2"
val MYSQL = "mysql"
  
def getDriver(dbType:String): JdbcProfile = {
scala.util.Properties.envOrElse("runMode", dbType) match {
case H2 => H2Driver
case MYSQL => MySQLDriver
case _ => MySQLDriver
}
}
}