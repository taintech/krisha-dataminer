package com.taintech.krisha.dataminer

/**
 * User: Rinat Tainov
 * Email: rinat@tainov.com
 * Date: 19.07.13
 * Time: 13:11
 */
case class Entity(id: Long, category: String,
                  price: String,
                  squareMeter: String,
                  rooms: Int,
                  city: String,
                  region: String,
                  address: String,
                  postDate: String,
                  condition: String,
                  floor: String,
                  houseDesc: String,
                  contactType: String,
                  contacts: String,
                  profileUrl: String,
                  summaryHtml: String,
                  profileHtml: String) {
  override def toString =
    s"""Entity{
  id: $id, category: $category,
  price: $price,squareMeter: $squareMeter,rooms: $rooms,
  city: $city,region: $region,address: $address,
  postDate: $postDate, condition: $condition, floor: $floor,
  ouseDesc: $houseDesc, contactType: $contactType, contacts: $contacts,
  profileUrl: $profileUrl,summaryHtml: $summaryHtml,profileHtml: $profileHtml
}"""
}

object Entity {
  def empty() = Entity(0L, "", "", "", 0, "", "", "", "", "", "", "", "", "", "", "", "")
}
