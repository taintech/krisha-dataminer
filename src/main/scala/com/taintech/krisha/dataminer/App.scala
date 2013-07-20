package com.taintech.krisha.dataminer

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.apache.log4j.LogManager
import akka.actor.{Props, ActorSystem}
import com.taintech.krisha.dataminer.actors.DB

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
    logger.info("App started!")
    val system = ActorSystem()
    val db = system.actorOf(Props[DB],"db")
    logger.info("Actors are started.")
    for (i <- 1 to 2) {
      val url1 = Constants.APARTMENTS_LIST_PAGE_URL.format(i)
      println("url: " + url1)
      if (isValidUrl(url1)) try {
        val doc = Jsoup.connect(url1).get()
        val iterator = doc.select(".item").iterator()
        println("step %s".format(i))
        Thread.sleep(100L)
        while (iterator.hasNext) {
          val item = iterator.next()
          Thread.sleep(100L)
          val url2 = item.select(".title a").attr("href")
          println(url2)
          if (isValidUrl(url2)) try {
            db ! Miner(item, Jsoup.connect(url2).get()).entity
          } catch {
            case e: Exception => logger.error("Problem with profile page.",e)
          }
        }
      } catch {
        case e: Exception => logger.error("Problem with list page.",e)
      }
    }
    system.shutdown()
    logger.info("Actors are stopped.")
  }

  def isValidUrl(url: String) = !url.trim.isEmpty
}

