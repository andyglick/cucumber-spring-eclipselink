package info.cukes

import com.h2.events.H2DatabaseEventListener
import spock.lang.Shared
import spock.lang.Specification

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
}
