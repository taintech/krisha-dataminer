package com.taintech.krisha.dataminer.actors

import com.taintech.krisha.dataminer.Entity
import akka.actor.Actor
import akka.event.Logging

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/20/13
 * Time: 2:22 PM
 */
class DB extends Actor{
  val log = Logging(context.system, this)
  def receive = {
    case entity: Entity => log.info("received test message")
    case _      ⇒ log.info("received unknown message")
  }
}
