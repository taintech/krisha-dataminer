package com.taintech.krisha.dataminer

/**
 * User: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 19.07.13
 * Time: 13:52
 */
object Constants {
  //  val SNIPPET_FOLDER = "D:\\development\\krisha-dataminer\\snippets\\"
  val SNIPPET_FOLDER = "/Users/taintech/development/krisha-dataminer/snippets/"
  val APARTMENTS_LIST_URL = "http://krisha.kz/prodazha/kvartiry/"
  val APARTMENTS_LIST_PAGE_URL = "http://krisha.kz/prodazha/kvartiry/?page=%s"

  val APARTMENTS = "kvartiry"
  val HOUSES = "doma"
  val SITE = "uchastkov"

  val BUY = "prodazha"
  val BUY_CATEGORIES = Array(APARTMENTS,HOUSES,SITE)

  val RENT = "arenda"

  val CITIES = Array("astana","almaty")
  val APARTMENTS_ROOMS = Array("1","2","3","4","5.99")

  val APARTMENTS_LIST_URLS_PATTERN = "http://krisha.kz/prodazha/kvartiry/$city/?page=$page&das%5Blive.rooms%5D=$rooms"

  val APARTMENTS_LIST_PAGE_URLS = {
    for{city <- CITIES
        rooms <- APARTMENTS_ROOMS}
      yield Pair(city+rooms,APARTMENTS_LIST_URLS_PATTERN.replace("$city",city).replace("$rooms",rooms))

  }
}
