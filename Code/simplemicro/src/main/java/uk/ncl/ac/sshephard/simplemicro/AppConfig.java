package uk.ncl.ac.sshephard.simplemicro;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Configuration
public class AppConfig {

  /*
   * Use the standard Cassandra driver API to create a com.datastax.driver.core.Session instance.
   */
  public @Bean Session session() {
    Cluster cluster = Cluster.builder().addContactPoints("52.50.71.127").build();
    return cluster.connect("Simplemicro");
  }
}
