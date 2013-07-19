package samples

import org.junit._
import Assert._
import com.taintech.krisha.dataminer.{Entity, Utils, Miner}
import com.taintech.krisha.dataminer.Constants._
import org.jsoup.nodes.{Document, Element}

@Test
class AppTest{
  @Test
  def testOK() = assertTrue(true)
}

@Test
class MinerTest {

  val item: Element = Utils.parseSnippet("item.html")
  val profile: Document = Utils.parseSnippet("profile-content.html","http://krisha.kz/a/show/6628069")
  val miner = Miner(item, profile)

  @Test
  def testTextMap() = assertFalse(miner.textMap.isEmpty)

  @Test
  def testForEmptyEntity() = assertNotEquals(miner.entity.id,Entity.empty())

  @Test
  def testForEntityProps() = {
    assertNotEquals(miner.entity.id,0L)
    assertNotEquals(miner.entity.category,"")
    assertNotEquals(miner.entity.price,"")
    assertNotEquals(miner.entity.squareMeter,"")
    assertNotEquals(miner.entity.rooms,0)
    assertNotEquals(miner.entity.city,"")
    assertNotEquals(miner.entity.region,"")
    assertNotEquals(miner.entity.address,"")
    assertNotEquals(miner.entity.postDate,"")
    assertNotEquals(miner.entity.condition,"")
    assertNotEquals(miner.entity.floor,"")
    assertNotEquals(miner.entity.houseDesc,"")
    assertNotEquals(miner.entity.contactType,"")
//    assertNotEquals(miner.entity.contacts,"")
    assertNotEquals(miner.entity.profileUrl,"")
    assertNotEquals(miner.entity.summaryHtml,"")
    assertNotEquals(miner.entity.profileHtml,"")

  }
}

@Test
class FunctionsTest {
  @Test
  def testIdExtractor = assertEquals(Miner.extractIdFromUrl("http://krisha.kz/a/show/6628069"),6628069)
  @Test
  def testCategoryExtractor = assertEquals(Miner.extractCategoryFromUrl(APARTMENTS_LIST_URL),"kvartiry")
}


