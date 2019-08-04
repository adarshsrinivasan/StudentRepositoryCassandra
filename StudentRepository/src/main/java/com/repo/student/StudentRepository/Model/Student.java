package com.repo.student.StudentRepository.Model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.stereotype.Component;

/**
 * Created by AdarshSrinivasan
 */
@Table("student")
public class Student {

  @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
  private Long roll;

  @Column("first_name")
  private String firstName;

  @Column("last_name")
  private String lastName;

  @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.ASCENDING)
  private String department;

  @Column
  private String club;

  @Column
  private Integer year;

  public Student(Long roll, String firstName, String lastName, String department,
      String club, Integer year) {
    this.roll = roll;
    this.firstName = firstName;
    this.lastName = lastName;
    this.department = department;
    this.club = club;
    this.year = year;
  }

  public Long getRoll() {
    return roll;
  }

  public void setRoll(Long roll) {
    this.roll = roll;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getClub() {
    return club;
  }

  public void setClub(String club) {
    this.club = club;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}
