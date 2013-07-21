package com.taintech.krisha.dataminer.actors

import com.taintech.krisha.dataminer.Entity
import akka.actor.Actor
import java.sql.{PreparedStatement, Connection, DriverManager}
import DB._
import org.apache.log4j.LogManager

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/20/13
 * Time: 2:22 PM
 */
class DB extends Actor {
  val logger = LogManager.getLogger(DB.getClass)
  var conn: Connection = null
  var stmt: PreparedStatement = null

  override def preStart() {
    conn = createConnection
    stmt = conn.prepareStatement(INSERT_QUERY)
  }

  override def postStop() {
    stmt.close()
    conn.close()
  }

  def receive = {
    case entity: Entity if entity.equals(Entity.empty()) => logger.info("empty message")
    case entity: Entity => {
      logger.info("received message start processing")
      save(entity)
      logger.info("entity saved")
    }
    case _ ⇒ logger.warn("received unknown message")
  }

  def save(entity: Entity) = try {
    stmt.setLong(1, entity.id)
    stmt.setString(2, entity.category)
    stmt.setString(3, entity.price)
    stmt.setString(4, entity.squareMeter)
    stmt.setInt(5, entity.rooms)
    stmt.setString(6, entity.city)
    stmt.setString(7, entity.region)
    stmt.setString(8, entity.address)
    stmt.setString(9, entity.postDate)
    stmt.setString(10, entity.condition)
    stmt.setString(11, entity.floor)
    stmt.setString(12, entity.houseDesc)
    stmt.setString(13, entity.contactType)
    stmt.setString(14, entity.contacts)
    stmt.setString(15, entity.profileUrl)
    stmt.setString(16, entity.summaryHtml)
    stmt.setString(17, entity.profileHtml)
    stmt.executeUpdate()
  } catch {
    case e: Exception => logger.error("Error saving entity.", e)
  }
}

object DB {
  val INSERT_QUERY = "INSERT INTO raw_entity_test (raw_id ,  category ,  price ,  square_meter ,  rooms ,  city ,  region ,  address ,  post_date ,  internal_condition ,  floor ,  house_desc ,  contact_type ,  contacts ,  profile_url ,  summary_html ,  profile_html ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
  val url = "jdbc:mysql://localhost:3306/"
  Class.forName("com.mysql.jdbc.Driver").newInstance()

  def createConnection: Connection = DB.createConnection("krisha?useUnicode=true&characterEncoding=UTF-8", "krisha", "krisha")

  def createConnection(schema: String, user: String, pwd: String): Connection = DriverManager.getConnection(url + schema, user, pwd)

}
