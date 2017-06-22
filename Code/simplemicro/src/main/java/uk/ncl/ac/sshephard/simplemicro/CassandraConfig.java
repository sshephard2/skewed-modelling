package uk.ncl.ac.sshephard.simplemicro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
@PropertySource(value = { "classpath:cassandra.properties" })
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Autowired
	private Environment env;
	
	/*
	 * Provide a contact point to the configuration.
	 */
	public String getContactPoints() {
		return env.getProperty("cassandra.contactpoints");
	}

	/*
	 * Provide a port to the configuration.
	 */
	public int getPort() {
		return Integer.parseInt(env.getProperty("cassandra.port").trim());
	}
	
	/*
	 * Provide a keyspace name to the configuration.
	 */
	public String getKeyspaceName() {
		return env.getProperty("cassandra.keyspace");
	}
}
