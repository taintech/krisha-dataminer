package com.taintech.krisha.dataminer

import akka.actor.Actor

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/20/13
 * Time: 3:04 PM
 */
object Greeter {
  case object Greet
  case object Done
}

class Greeter extends Actor {
  def receive = {
    case Greeter.Greet ⇒
      println("Hello World!")
      sender ! Greeter.Done
  }
}