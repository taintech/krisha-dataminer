package com.taintech.krisha.dataminer.actors

import akka.actor.{ActorRef, Actor}
import org.apache.log4j.LogManager
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import com.taintech.krisha.dataminer.Miner

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/21/13
 * Time: 5:31 AM
 */
class Crawler extends Actor {

  val logger = LogManager.getLogger(DB.getClass)

  def receive = {
    case Pair(url: String, db: ActorRef) => try {
      def itemsUrl(page: String) = url.replace("$page", page)
      val itemsFirstUrl: String = itemsUrl("1")
      logger.info("opening page 1 url: " + itemsFirstUrl)
      var itemsDoc = getDocument(itemsFirstUrl)
      if (itemsDoc.nonEmpty) {
        iterateItems(itemsDoc.get, db)
        val maxPage = itemsDoc.get.select("table#pager tbody tr td span a").last.text.toInt
        for(page <- 2 to maxPage){
          logger.info("opening page %d url: ".format(page) + itemsUrl(page+""))
          itemsDoc = getDocument(itemsUrl(page+""))
          if(itemsDoc.nonEmpty) iterateItems(itemsDoc.get, db)
        }
      }
    } catch {
      case e: Exception => logger.error("unknown error", e)
    }
    case _ ⇒ logger.warn("received unknown message")
  }

  def iterateItems(doc: Document, db: ActorRef) {
    val iterator = doc.select(".item").iterator()
    //TODO must implement wait, seems like ddos
//    wait(100L)
    while (iterator.hasNext) {
      val item = iterator.next()
//      wait(100L)
      val profileUrl = item.select(".title a").attr("href")
      logger.info("opening profile url: " + profileUrl)
      if (!profileUrl.trim.isEmpty) {
        val profileDoc = getDocument(profileUrl)
        if (profileDoc.nonEmpty) db ! Miner(item, profileDoc.get).entity
      }
    }
  }

  def getDocument(url: String) = try {
    Some(Jsoup.connect(url).get())
  } catch {
    case e: Exception => {
      logger.error("Cannot open url: " + url, e)
      None
    }
  }
}
