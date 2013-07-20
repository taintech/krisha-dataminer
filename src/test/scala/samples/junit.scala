package samples

import org.junit._
import Assert._
import com.taintech.krisha.dataminer.{Entity, Utils, Miner}
import com.taintech.krisha.dataminer.Constants._
import org.jsoup.nodes.{Document, Element}
import com.taintech.krisha.dataminer.actors.DB
import akka.actor.{ActorSystem, Props}
import java.sql.{SQLException, Connection}
import org.apache.log4j.LogManager

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
class MinerEmptyTest {

  val item: Element = Utils.parseSnippet("item-empty.html")
  val profile: Document = Utils.parseSnippet("profile-content-empty.html","http://krisha.kz/a/show/6628069")
  val miner = Miner(item, profile)

  @Test
  def testTextMap() = assertTrue(miner.textMap.isEmpty)

  @Test
  def testForEntityProps() = {
    assertEquals(miner.entity.price,"")
    assertEquals(miner.entity.squareMeter,"")
    assertEquals(miner.entity.rooms,0)
    assertEquals(miner.entity.city,"")
    assertEquals(miner.entity.region,"")
    assertEquals(miner.entity.address,"")
    assertEquals(miner.entity.postDate,"")
    assertEquals(miner.entity.condition,"")
    assertEquals(miner.entity.floor,"")
    assertEquals(miner.entity.houseDesc,"")
    assertEquals(miner.entity.contactType,"")
    //    assertEquals(miner.entity.contacts,"")

  }
}

@Test
class FunctionsTest {
  @Test
  def testIdExtractor = assertEquals(Miner.extractIdFromUrl("http://krisha.kz/a/show/6628069"),6628069)
  @Test
  def testCategoryExtractor = assertEquals(Miner.extractCategoryFromUrl(APARTMENTS_LIST_URL),"kvartiry")
}


@Test
class DatabaseTest{

  val logger = LogManager.getLogger("DatabaseTest")

  @Test
  def testSelect{
    var conn: Connection  = null
    try{
      conn = DB.createConnection("test","","")
      val stmt = conn.createStatement()
      val rs = stmt.executeQuery("""select 1 "test" from dual""")
      assertTrue(rs.next())
      assertEquals(rs.getInt("test"),1)
      rs.close()
      stmt.close()
    }catch {
      case e: Exception => logger.error("Problem with database!", e)
    }
    finally {
      conn.close()
    }

  }
}

@Test
class DBActorTest{
  @Test
  def testMessage = {
    val system = ActorSystem()
    val db = system.actorOf(Props[DB],"db")
    db!Entity.empty()
    system.shutdown()
  }
}


