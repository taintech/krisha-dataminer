package com.taintech.krisha.dataminer

import java.io.File
import org.jsoup.Jsoup

/**
 * Author: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 7/19/13
 * Time: 5:36 AM
 */
object Utils {
  def parseSnippet(fileName: String) = {
    val input = new File("/Users/taintech/development/krisha-dataminer/snippets/"+fileName)
    Jsoup.parse(input,"UTF-8", "http://krisha.kz/")
  }
}
