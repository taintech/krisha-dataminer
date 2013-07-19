package com.taintech.krisha.dataminer

import org.jsoup.nodes.{Element, Document}
import Miner._

/**
 * User: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 19.07.13
 * Time: 14:08
 */
class Miner(val item: Element, val profile: Document) {

  val textMap = {
    var map = Map.empty[String, String]
    val it = profile.select("table.fullInfo.colsCont tbody tr").iterator()
    while (it.hasNext) {
      val tr = it.next()
      val text = tr.text()
      if (text.contains("Дом")) map += extract("houseDesc", tr)
      else if (text.contains("Этаж")) map += extract("floor", tr)
      else if (text.contains("Площадь")) map += extract("squareMeter", tr)
      else if (text.contains("Состояние")) map += extract("condition", tr)
    }
    def extract(prop: String, tr: Element) = prop -> tr.select("td").last().text().trim
    map
  }

  val addressArray = item.select("div.title a").attr("title").split(",")

  val rooms = item.select("div.title a").text().split('-').head.toInt

  val postDate = item.select("div.item div.descr small.gray span.gray.air").last.text()

  val contactType = profile.select("table.fullInfo tbody tr.owner td.name").text()

  val entity: Entity =
    Entity(
      extractIdFromUrl(profile.baseUri()), extractCategoryFromUrl(item.baseUri()),
      profile.select("span.price").text(), textMap("squareMeter"), rooms,
      addressArray(0), addressArray(1), addressArray(2),
      postDate, textMap("condition"), textMap("floor"),
      textMap("houseDesc"), contactType, "",
      profile.baseUri(), item.toString, profile.toString
    )

}

object Miner {
  def apply(item: Element, profile: Document) = new Miner(item, profile)

  def extractIdFromUrl(url: String) = url.split("/").last.toLong

  def extractCategoryFromUrl(url: String) = url.split("/").last
}
