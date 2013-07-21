package com.taintech.krisha.dataminer

import org.jsoup.Jsoup
import org.apache.log4j.LogManager
import akka.actor.{Props, ActorSystem}
import com.taintech.krisha.dataminer.actors.{Crawler, DB}
import com.taintech.krisha.dataminer.Constants._

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/19/13
 * Time: 5:03 AM
 */
object App {

  val logger = LogManager.getLogger(App.getClass)

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    //TODO write to log execution time
    logger.info("App started!")
    val system = ActorSystem()
    logger.info("Actors are started.")
    for(url_patterns <- APARTMENTS_LIST_PAGE_URLS){
      system.actorOf(Props[Crawler], url_patterns._1) ! Pair(url_patterns._2,system.actorOf(Props[DB], url_patterns._1+"db"))
    }
    //TODO stop while executing,now can stop only when finished
    import scala.util.control.Breaks._
    breakable {
      for( ln <- io.Source.stdin.getLines )
        if(ln.trim.toUpperCase.equals("STOP")) break
        else println("""Write "stop" to end program:""")
    }
    system.shutdown()
    logger.info("Actors are stopped.")
  }

  def isValidUrl(url: String) = !url.trim.isEmpty
}

