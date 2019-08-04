package com.repo.student.StudentRepository.Utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by asriniva
 */
@Component
public class CassandraUtils {

  @Value("${spring.data.cassandra.keyspace-name}")
  private String keyspace;

  @Value("${spring.data.cassandra.contact-points}")
  private String hosts;

  @Value("${spring.data.cassandra.user-name}")
  private String userName;

  @Value("${spring.data.cassandra.password}")
  private String password;

  private Cluster cluster;
  private Session session;

  private static final Logger LOGGER =
      LoggerFactory.getLogger(CassandraUtils.class);

  public void initialize(){
    cluster = Cluster.builder().addContactPoint(hosts).withCredentials(userName, password).build();
    session = cluster.connect();

    String createKeySpace = "CREATE KEYSPACE IF NOT EXISTS " + keyspace +" WITH REPLICATION = "
        + "{'class' : 'SimpleStrategy', 'replication_factor' : 1};";
    session.execute(createKeySpace);
    LOGGER.info("Created keyspace");


    String createTableStudent = "CREATE TABLE IF NOT EXISTS " + keyspace
        + ".student (roll int, first_name text, last_name text, department "
        + "text, club text, year int, PRIMARY KEY (department, roll, year))";
    session.execute(createTableStudent);
    LOGGER.info("Created createTableStudent");

    String createViewByRollASC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace
        + ".student_by_roll_ASC AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL"
        + " AND roll IS NOT NULL AND department IS NOT NULL PRIMARY KEY (year, roll, department) WITH "
        + "CLUSTERING ORDER BY (roll ASC);";
    session.execute(createViewByRollASC);
    LOGGER.info("Created ViewByRollASC");

    String createViewByRollDESC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace
        + ".student_by_roll_DESC AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL"
        + " AND roll IS NOT NULL AND department IS NOT NULL PRIMARY KEY (year, roll, department) WITH "
        + "CLUSTERING ORDER BY (roll DESC);";
    session.execute(createViewByRollDESC);
    LOGGER.info("Created ViewByRollDESC");

    String createViewByDepartmentASC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace
        + ".student_by_department_ASC AS SELECT * FROM " + keyspace +
        ".student WHERE year IS NOT NULL AND department IS NOT NULL AND roll IS NOT NULL PRIMARY "
        + "KEY (year, department, roll) WITH CLUSTERING ORDER BY (department ASC);";
    session.execute(createViewByDepartmentASC);
    LOGGER.info("Created ViewByDepartmentASC");

    String createViewByDepartmentDESC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace
        + ".student_by_department_DESC AS SELECT * FROM " + keyspace +
        ".student WHERE year IS NOT NULL AND department IS NOT NULL AND roll IS NOT NULL "
        + "PRIMARY KEY (year, department, roll) WITH CLUSTERING ORDER BY (department DESC);";
    session.execute(createViewByDepartmentDESC);
    LOGGER.info("Created ViewByDepartmentDESC");

    String createViewByClubASC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace + ".student_by_club_ASC "
        + "AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL AND department IS "
        + "NOT NULL AND club IS NOT NULL AND roll IS NOT NULL PRIMARY KEY (year, club, "
        + "department, roll) WITH CLUSTERING ORDER BY (club ASC);";
    session.execute(createViewByClubASC);
    LOGGER.info("Created ViewByClubASC");

    String createViewByClubDESE = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace + ".student_by_club_DESC "
        + "AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL AND department "
        + "IS NOT NULL AND club IS NOT NULL AND roll IS NOT NULL PRIMARY KEY (year, club, "
        + "department, roll) WITH CLUSTERING ORDER BY (club DESC);";
    session.execute(createViewByClubDESE);
    LOGGER.info("Created ViewByClubDESE");

    String createViewByFirstNameASC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace +
        ".student_by_first_name_ASC AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL "
        + "AND department IS NOT NULL AND roll IS NOT NULL AND first_name IS NOT NULL "
        + "PRIMARY KEY (year, first_name, department, roll) WITH CLUSTERING ORDER BY (first_name ASC);";
    session.execute(createViewByFirstNameASC);
    LOGGER.info("Created ViewByFirstNameASC");

    String createViewByFirstNameDESC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace +
        ".student_by_first_name_DESC AS SELECT * FROM " + keyspace + ".student WHERE year IS NOT NULL "
        + "AND department IS NOT NULL AND roll IS NOT NULL AND first_name IS NOT NULL "
        + "PRIMARY KEY (year, first_name, department, roll) WITH CLUSTERING ORDER BY (first_name DESC);";
    session.execute(createViewByFirstNameDESC);
    LOGGER.info("Created ViewByFirstNameDESC");

    String createViewByLastNameASC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace +
        ".student_by_last_name_ASC AS SELECT * FROM " + keyspace + ".student "
        + "WHERE year IS NOT NULL AND department IS NOT NULL AND roll IS NOT NULL AND last_name "
        + "IS NOT NULL PRIMARY KEY (year, last_name, department, roll) WITH CLUSTERING ORDER BY "
        + "(last_name ASC);";
    session.execute(createViewByLastNameASC);
    LOGGER.info("Created ViewByLastNameASC");

    String createViewByLastNameDESC = "CREATE MATERIALIZED VIEW IF NOT EXISTS " + keyspace +
        ".student_by_last_name_DESC AS SELECT * FROM " + keyspace + ".student "
        + "WHERE year IS NOT NULL AND department IS NOT NULL AND roll IS NOT NULL AND last_name "
        + "IS NOT NULL PRIMARY KEY (year, last_name, department, roll) WITH CLUSTERING ORDER BY (last_name DESC);";
    session.execute(createViewByLastNameDESC);
    LOGGER.info("Created ViewByLastNameDESC");
  }

  public Cluster getCluster() {
    return cluster;
  }

  public void setCluster(Cluster cluster) {
    this.cluster = cluster;
  }

  public Session getSession() {
    return session;
  }

  public void setSession(Session session) {
    this.session = session;
  }
}
