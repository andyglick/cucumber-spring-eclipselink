package com.h2.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import ch.qos.logback.classic.Level;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;

/**
 * @author glick
 */
@SuppressWarnings("unused")
public class H2DatabaseEventListener implements org.h2.api.DatabaseEventListener
{
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtThreadExceptionHandler());

//    if (log.getClass().getName().equals("ch.qos.logback.classic.Logger")) {
//      ((ch.qos.logback.classic.Logger)log).setLevel(Level.WARN);
//    }
  }

  @Override
  public void init(String url) {
    if (log.isWarnEnabled() || log.isInfoEnabled() || log.isDebugEnabled())
    {
      log.warn(String.format("H2DatabaseEventListener received URL is %s", url));
    }
  }

  @Override
  public void opened()
  {
    if (log.isWarnEnabled() || log.isInfoEnabled() || log.isDebugEnabled())
    {
      log.warn("H2DatabaseEventListener::opened");
    }
  }

  @Override
  public void exceptionThrown(SQLException e, String sql)
  {
    if (log.isWarnEnabled() || log.isInfoEnabled() || log.isDebugEnabled())
    {
      log.warn("H2DatabaseEventListener::exceptionThrown");
      log.warn(String.format("sql statement was %s", sql));
      log.warn("the exception is ", e);
    }
  }

  @Override
  public void setProgress(int state, String name, int x, int max)
  {
    if (log.isWarnEnabled() || log.isInfoEnabled() || log.isDebugEnabled())
    {
      log.warn("H2DatabaseEventListener::setProgress");
      log.warn(String.format("state is %d name is %s x is %d max is %d", state, name, x, max));
    }
  }

  @Override
  public void closingDatabase()
  {
    if (log.isWarnEnabled() || log.isInfoEnabled() || log.isDebugEnabled())
    {
      log.warn("H2DatabaseEventListener::closingDatabase");
    }
  }

  public static class UncaughtThreadExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
      log.error("UncaughtThreadExceptionHandler invoked");
      log.error("the thread's name is " + t.getName());
      log.error("the exception was " + e.getClass().getSimpleName());
    }
  }
}
