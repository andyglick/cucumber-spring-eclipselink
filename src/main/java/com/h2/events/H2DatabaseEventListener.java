package com.h2.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author glick
 */
@SuppressWarnings("unused")
public class H2DatabaseEventListener implements org.h2.api.DatabaseEventListener
{
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  static {
    Thread.setDefaultUncaughtExceptionHandler(new UncaughtThreadExceptionHandler());
  }

  @Override
  public void init(String url) {

    String message = String.format("H2DatabaseEventListener received URL is %s", url);


  }

  @Override
  public void opened() {

    String message = "H2DatabaseEventListener::opened";

    emitLogMessageConditionally(message);
  }

  @Override
  public void exceptionThrown(SQLException e, String sql)
  {
    List<String> messageList = Arrays.asList("H2DatabaseEventListener::exceptionThrown",
      String.format("sql statement was %s", sql), String.format("the exception is %s", e));

    for (String message : messageList) {
      emitLogMessageConditionally(message);
    }
  }

  @Override
  public void setProgress(int state, String name, int x, int max)
  {
    List<String> messageList = Arrays.asList("H2DatabaseEventListener::setProgress",
      String.format("state is %d name is %s x is %d max is %d", state, name, x, max));

    for (String message : messageList) {
      emitLogMessageConditionally(message);
    }
  }

  @Override
  public void closingDatabase()
  {
    emitLogMessageConditionally("H2DatabaseEventListener::closingDatabase");
  }

  public static class UncaughtThreadExceptionHandler implements Thread.UncaughtExceptionHandler
  {
    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
      H2DatabaseEventListener listener = new H2DatabaseEventListener();

      List<String> messageList = Arrays.asList("UncaughtThreadExceptionHandler invoked",
        String.format("the thread's name is %s", t.getName()),
        String.format("the exception was %s", e.getClass().getSimpleName()));

      for (String message : messageList) {
        listener.emitLogMessageConditionally(message);
      }
    }
  }

  private void emitLogMessageConditionally(String message) {
    if (log.isDebugEnabled()) {
      log.debug(message);
    } else if (log.isInfoEnabled()) {
      log.info(message);
    } else if (log.isWarnEnabled()) {
      log.warn(message);
    }
  }
}
