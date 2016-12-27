package com.github.marschall.storedprocedureproxy.examples;

import static org.junit.Assert.assertEquals;

import java.sql.Types;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.OutParameters;

public class JdbiTest extends AbstractExampleTest {

  private Handle handle;

  @Before
  public void setUp() {
    DBI dbi = new DBI(this.getDataSource());
    this.handle = dbi.open();
  }

  @Test
  public void call() {
    assertEquals(2, plus1inout(1));
  }

  @After
  public void tearDown() {
    this.handle.close();
  }

  private int plus1inout(int argument) {
    OutParameters outParameters = handle.createCall("call plus1inout(?, ?);")
            .bind(0, argument)
            .registerOutParameter(1, Types.INTEGER)
            .invoke();
    return outParameters.getInt(2);
  }

}
