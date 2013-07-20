package com.taintech.krisha.dataminer

import org.jsoup.nodes.{Element, Document}
import Miner._
import org.apache.log4j.LogManager

/**
 * User: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 19.07.13
 * Time: 14:08
 */
class Miner(val item: Element, val profile: Document) {

  val textMap = {
    var map = Map.empty[String, Option[String]]
    try {

      val it = profile.select("table.fullInfo.colsCont tbody tr").iterator()
      while (it.hasNext) {
        val tr = it.next()
        val text = tr.text()
        if (text.contains("Дом")) map += extract("houseDesc", tr)
        else if (text.contains("Этаж")) map += extract("floor", tr)
        else if (text.contains("Площадь")) map += extract("squareMeter", tr)
        else if (text.contains("Состояние")) map += extract("condition", tr)
      }
      def extract(prop: String, tr: Element) = prop -> Some(tr.select("td").last().text().trim)
    } catch {
      case e: NullPointerException => logger.warn("null textMap url:" + profile.baseUri())
      case e: Exception => logger.error("Unknown Error url:" + profile.baseUri(),e)
    }
    map
  }

  val addressArray = try{
    val res = item.select("div.title a").attr("title").split(",").to[scala.collection.mutable.ArrayBuffer]
    while(res.size != 3 && !(res.size>3) ) res+=""
    res.toArray
  }catch {
    case e: Exception => {
      logger.error("Unknown Error url:" + item.baseUri() + " profile url:" + profile.baseUri(),e)
      Array("","","")
    }
  }

  val rooms: Option[Int] = try {
    Some(item.select("div.title a").text().split('-').head.toInt)
  } catch {
    case e: NumberFormatException => {
      logger.warn("Cannot convert to Int", e)
      None
    }
    case e: Exception => {
      logger.error("Unknown Error url:" + item.baseUri() + " profile url:" + profile.baseUri(),e)
      None
    }
  }

  val postDate = try {
    Some(item.select("div.descr small.gray span.gray.air").last.text())
  }catch {
    case e: Exception => {
      logger.error("Unknown Error url:" + item.baseUri() + " profile url:" + profile.baseUri(),e)
      None
    }
  }

  val contactType = try {
    Some(profile.select("table.fullInfo tbody tr.owner td.name").text())
  }catch {
    case e: Exception => {
      logger.error("Unknown Error url:" + item.baseUri() + " profile url:" + profile.baseUri(),e)
      None
    }
  }

  val entity: Entity =
    Entity(
      extractIdFromUrl(profile.baseUri()), extractCategoryFromUrl(item.baseUri()),
      profile.select("span.price").text(), textMapWrapper("squareMeter").getOrElse(""), rooms.getOrElse(0),
      addressArray(0), addressArray(1), addressArray(2),
      postDate.getOrElse(""), textMapWrapper("condition").getOrElse(""), textMapWrapper("floor").getOrElse(""),
      textMapWrapper("houseDesc").getOrElse(""), contactType.getOrElse(""), "",
      profile.baseUri(), item.toString, profile.toString
    )

  def textMapWrapper(key: String) = if(textMap.contains(key)) textMap(key) else None
}

object Miner {

  val logger = LogManager.getLogger(Miner.getClass)

  def apply(item: Element, profile: Document) = new Miner(item, profile)

  def extractIdFromUrl(url: String) = url.split("/").last.toLong

  def extractCategoryFromUrl(url: String) = url.split("/").last
}
