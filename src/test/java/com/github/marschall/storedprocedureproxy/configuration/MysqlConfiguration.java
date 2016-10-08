package com.github.marschall.storedprocedureproxy.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
public class MysqlConfiguration {

  @Bean
  public DataSource dataSource() {
    SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    dataSource.setSuppressClose(true);
    String userName = System.getProperty("user.name");
    String database = userName;
    // https://dev.mysql.com/doc/connector-j/6.0/en/connector-j-reference-configuration-properties.html
    dataSource.setUrl("jdbc:mysql://localhost:3306/" + database);
    dataSource.setUsername(userName);
    String password = this.isTravis() ? "" : userName;
    dataSource.setPassword(password);
    return dataSource;
  }

  private boolean isTravis() {
    return System.getenv().getOrDefault("TRAVIS", "false").equals("true");
  }

}
