package groovy.support

import spock.lang.Shared
import spock.lang.Specification

import static groovy.support.GroovySupport.isDifferentObject as isDifferentObject
import static groovy.support.GroovySupport.isSameObject as isSameObject


/**
 * @author glick
 */
class GroovySupportSpec extends Specification {

  def "cover the GroovySupport class"() {
    isDifferentObject("abcd", "abcd")

    isSameObject("abcd", "abcd")
  }
}
