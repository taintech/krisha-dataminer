package com.taintech.krisha.dataminer

import java.io.File
import org.jsoup.Jsoup
import Constants._
import org.jsoup.nodes.{Element, Document}

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/19/13
 * Time: 5:36 AM
 */
object Utils {
  def parseSnippet(fileName: String): Element = {
    val input = new File(SNIPPET_FOLDER+fileName)
    Jsoup.parse(input,"UTF-8", APARTMENTS_LIST_URL)
  }
  def parseSnippet(fileName: String, baseUrl: String): Document = {
    val input = new File(SNIPPET_FOLDER+fileName)
    Jsoup.parse(input,"UTF-8", baseUrl)
  }
}
