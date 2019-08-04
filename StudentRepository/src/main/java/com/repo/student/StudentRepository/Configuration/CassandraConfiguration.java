package com.repo.student.StudentRepository.Configuration;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.repo.student.StudentRepository.Utils.CassandraUtils;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Created by AdarshSrinivasan
 */
@Configuration
@EnableCassandraRepositories
public class CassandraConfiguration extends AbstractCassandraConfiguration {

  @Autowired
  private CassandraUtils cassandraUtils;

  private final String keyspace;
  private final String hosts;
  private final String userName;
  private final String password;
  private final String schemaAction;

  private Cluster cluster;
  private Session session;

  CassandraConfiguration(
      @Value("${spring.data.cassandra.keyspace-name}") String keyspace,
      @Value("${spring.data.cassandra.contact-points}") String hosts,
      @Value("${spring.data.cassandra.user-name}") String userName,
      @Value("${spring.data.cassandra.password}") String password,
      @Value("${spring.data.cassandra.schema-action}") String schemaAction) {
    this.keyspace = keyspace;
    this.hosts = hosts;
    this.userName = userName;
    this.password = password;
    this.schemaAction = schemaAction;
  }
  @Bean
  @Override
  public CassandraClusterFactoryBean cluster() {

    RetryingCassandraClusterFactoryBean bean = new RetryingCassandraClusterFactoryBean();

    bean.setAddressTranslator(getAddressTranslator());
    bean.setAuthProvider(getAuthProvider());
    bean.setClusterBuilderConfigurer(getClusterBuilderConfigurer());
    bean.setClusterName(getClusterName());
    bean.setCompressionType(getCompressionType());
    bean.setContactPoints(getContactPoints());
    bean.setLoadBalancingPolicy(getLoadBalancingPolicy());
    bean.setMaxSchemaAgreementWaitSeconds(getMaxSchemaAgreementWaitSeconds());
    bean.setMetricsEnabled(getMetricsEnabled());
    bean.setNettyOptions(getNettyOptions());
    bean.setPoolingOptions(getPoolingOptions());
    bean.setPort(getPort());
    bean.setProtocolVersion(getProtocolVersion());
    bean.setQueryOptions(getQueryOptions());
    bean.setReconnectionPolicy(getReconnectionPolicy());
    bean.setRetryPolicy(getRetryPolicy());
    bean.setSpeculativeExecutionPolicy(getSpeculativeExecutionPolicy());
    bean.setSocketOptions(getSocketOptions());
    bean.setTimestampGenerator(getTimestampGenerator());

    bean.setKeyspaceCreations(getKeyspaceCreations());
    bean.setKeyspaceDrops(getKeyspaceDrops());
    bean.setStartupScripts(getStartupScripts());
    bean.setShutdownScripts(getShutdownScripts());
    bean.setUsername(userName);
    bean.setPassword(password);

    cassandraUtils.initialize();
    return bean;
  }

  @Override
  protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
    final CreateKeyspaceSpecification specification =
        CreateKeyspaceSpecification.createKeyspace(keyspace)
            .ifNotExists()
            .with(KeyspaceOption.DURABLE_WRITES, true)
            .withSimpleReplication();
    return Arrays.asList(specification);
  }

  @Override
  public SchemaAction getSchemaAction() {
    return SchemaAction.valueOf(schemaAction);
  }

  @Override
  public String[] getEntityBasePackages() {
    return new String[]{"com.repo.student.StudentRepository"};
  }

  @Override
  protected String getContactPoints() {
    return hosts;
  }

  @Override
  protected String getKeyspaceName() {
    return keyspace;
  }

}
