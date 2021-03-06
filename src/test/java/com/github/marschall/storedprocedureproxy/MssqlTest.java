package com.github.marschall.storedprocedureproxy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import com.github.marschall.storedprocedureproxy.ProcedureCallerFactory.ParameterRegistration;
import com.github.marschall.storedprocedureproxy.configuration.MssqlConfiguration;
import com.github.marschall.storedprocedureproxy.procedures.MssqlProcedures;

@Sql("classpath:sql/mssql_procedures.sql")
@ContextConfiguration(classes = MssqlConfiguration.class)
@DisabledOnTravis
public class MssqlTest extends AbstractDataSourceTest {

  private MssqlProcedures procedures(ParameterRegistration parameterRegistration) {
    return ProcedureCallerFactory.of(MssqlProcedures.class, this.getDataSource())
            .withParameterRegistration(parameterRegistration)
            .build();
  }

  @IndexedParametersRegistrationTest
  public void plus1inout(ParameterRegistration parameterRegistration) {
    assertEquals(2, this.procedures(parameterRegistration).plus1inout(1));
  }

  @IndexedParametersRegistrationTest
  public void plus1inret(ParameterRegistration parameterRegistration) {
    assertEquals(2, this.procedures(parameterRegistration).plus1inret(1));
  }

  @IndexedParametersRegistrationTest
  public void fakeCursor(ParameterRegistration parameterRegistration) {
    assertEquals(Arrays.asList("hello", "world"), this.procedures(parameterRegistration).fakeCursor());
  }

}
