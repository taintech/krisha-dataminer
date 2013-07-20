package com.taintech.krisha.dataminer

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/20/13
 * Time: 3:04 PM
 */
import akka.actor.Actor
import akka.actor.Props

class HelloWorld extends Actor {

  override def preStart(): Unit = {
    // create the greeter actor
    val greeter = context.actorOf(Props[Greeter], "greeter")
    // tell it to perform the greeting
    greeter ! Greeter.Greet
  }

  def receive = {
    // when the greeter is done, stop this actor and with it the application
    case Greeter.Done ⇒ context.stop(self)
  }
}
