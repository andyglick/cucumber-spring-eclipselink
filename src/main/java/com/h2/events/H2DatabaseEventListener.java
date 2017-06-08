package com.h2.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

/**
 * @author glick
 */
@SuppressWarnings("unused")
public class H2DatabaseEventListener implements org.h2.api.DatabaseEventListener
{
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Override
  public void init(String url)
  {
    log.warn(String.format("H2DatabaseEventListener received URL is %s", url));
  }

  @Override
  public void opened()
  {
    log.warn("H2DatabaseEventListener::opened");
  }

  @Override
  public void exceptionThrown(SQLException e, String sql)
  {
    log.warn("H2DatabaseEventListener::exceptionThrown");
    log.warn(String.format("sql statement was %s", sql));
    log.warn("the exception is ", e);
  }

  @Override
  public void setProgress(int state, String name, int x, int max)
  {
    log.warn("H2DatabaseEventListener::setProgress");
    log.warn(String.format("state is %d name is %s x is %d max is %d", state, name, x, max));
  }

  @Override
  public void closingDatabase()
  {
    log.warn("H2DatabaseEventListener::closingDatabase");
  }
}
