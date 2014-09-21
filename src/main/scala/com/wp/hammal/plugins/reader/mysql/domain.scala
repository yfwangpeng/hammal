package com.wp.hammal.plugins.reader.mysql

import scala.slick.driver.JdbcProfile
import java.sql.Date
//define driver
trait Profile {
val profile: JdbcProfile
}