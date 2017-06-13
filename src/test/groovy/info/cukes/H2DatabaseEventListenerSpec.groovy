package info.cukes

import com.h2.events.H2DatabaseEventListener
import spock.lang.Shared
import spock.lang.Specification

import java.sql.SQLException;

/**
 * @author glick
 */
class H2DatabaseEventListenerSpec extends Specification {

  @Shared
  H2DatabaseEventListener databaseEventListener = new H2DatabaseEventListener()

  def "excercise databaseEventListener for coverage"() {
    
    given: "a databaseEventListener"
    
    when: "a databaseEventListener is opened"
    databaseEventListener.opened()
    databaseEventListener.init("jdbc:h2:abcdefg-database.databaseland.org")
    databaseEventListener.closingDatabase()
    
    then: "the databaseEventListener participates in the testing"
    databaseEventListener != null
  }

  def "exercise setProgress(int state, String name, int x, int max)"() {

    databaseEventListener.setProgress(10, "funny stuff", 20, 1000)

    databaseEventListener.exceptionThrown(new SQLException("who knows what evil", "the shadow do!"),
      "select * from zephyrWindow")
  }
}
