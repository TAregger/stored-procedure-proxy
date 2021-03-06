package com.github.marschall.storedprocedureproxy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;

import com.github.marschall.storedprocedureproxy.ProcedureCallerFactory.ProcedureCaller;

public class NumberedValueExtractorResultExtractorTest {

  @Test
  public void valueExtractorArguments() throws SQLException {
    // set up
    DataSource dataSource = mock(DataSource.class);
    CallableStatement callableStatement = mock(CallableStatement.class);
    Connection connection = mock(Connection.class);
    DatabaseMetaData metaData = mock(DatabaseMetaData.class);
    ResultSet resultSet = mock(ResultSet.class);

    when(dataSource.getConnection()).thenReturn(connection);
    when(connection.getMetaData()).thenReturn(metaData);
    when(metaData.getDatabaseProductName()).thenReturn("junit");
    when(connection.prepareCall(anyString())).thenReturn(callableStatement);
    when(callableStatement.execute()).thenReturn(true);
    when(callableStatement.getResultSet()).thenReturn(resultSet);

    NumberedValueExtractor<String> valueExtractor = mock(NumberedValueExtractor.class);
    SampleInterface procedures = ProcedureCallerFactory.build(SampleInterface.class, dataSource);

    // actual behavior
    when(resultSet.next()).thenReturn(true, true, false);
    when(valueExtractor.extractValue(eq(resultSet), anyInt())).thenReturn("s");

    // when
    procedures.extractString(valueExtractor);

    // then
    verify(valueExtractor, times(1)).extractValue(resultSet, 0);
    verify(valueExtractor, times(1)).extractValue(resultSet, 1);
  }

  @Test
  public void testToString()  {
    ResultExtractor extractor = new NumberedValueExtractorResultExtractor(1, ProcedureCaller.DEFAULT_FETCH_SIZE);
    assertEquals("NumberedValueExtractorResultExtractor[methodParameterIndex=1, fetchSize=default]", extractor.toString());

    extractor = new NumberedValueExtractorResultExtractor(1, 10);
    assertEquals("NumberedValueExtractorResultExtractor[methodParameterIndex=1, fetchSize=10]", extractor.toString());
  }

  interface SampleInterface {

    List<String> extractString(NumberedValueExtractor<String> valueExtractor);

  }

}
