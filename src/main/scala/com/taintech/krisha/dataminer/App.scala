package com.taintech.krisha.dataminer

import org.jsoup.Jsoup
import org.jsoup.nodes.Element

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/19/13
 * Time: 5:03 AM
 */
object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    //    println( "Hello World!" )
    //    println("concat arguments = " + foo(args))
    //    Console println Utils.parseSnippet("item.html").select(".title a").attr("href")
    for (i <- 1 to 2) yield {
      val url1 = Constants.APARTMENTS_LIST_PAGE_URL.format(i)
      println("url: " + url1)
      if (isValidUrl(url1)) try{
        val doc = Jsoup.connect(url1).get()
        val iterator = doc.select(".item").iterator()
        println("step %s".format(i))
        Thread.sleep(100L)
        while (iterator.hasNext) {
          val item = iterator.next()
          Thread.sleep(100L)
          val url2 = item.select(".title a").attr("href")
          println(url2)
          if (isValidUrl(url2)) try{
            Console println Miner(item, Jsoup.connect(url2).get()).entity
          }catch {
            case e: Exception => e.printStackTrace()
          }
        }
      }catch{
        case e: Exception => e.printStackTrace()
      }
    }
  }

  def isValidUrl(url: String) = !url.trim.isEmpty
}

