package info.cukes;

import org.junit.Ignore;
import org.junit.Test;

import com.h2.events.H2DatabaseEventListener;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author glick
 */
public class H2DatabaseEventListenerTest
{
  H2DatabaseEventListener databaseEventListener = new H2DatabaseEventListener();

  @Test
  public void excerciseDatabaseEventListener()
  {
    databaseEventListener.opened();
    databaseEventListener.init("jdbc:h2:abcdefg-database.databaseland.org");
    databaseEventListener.closingDatabase();

    assertThat(databaseEventListener).isNotNull();
  }

  @Test(expected = SQLException.class)
  @Ignore
  public void whoButTheShadowKnows() {

    databaseEventListener.setProgress(10, "funny stuff", 20, 1000);

    databaseEventListener.exceptionThrown(new SQLException("who knows what evil ", "the shadow do!"),
      "select * from zephyrWindow");
  }
}
