package com.repo.student.StudentRepository.Service.Impl;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select.Where;
import com.repo.student.StudentRepository.Exceptions.InvalidDataException;
import com.repo.student.StudentRepository.Exceptions.StudentNotFound;
import com.repo.student.StudentRepository.Model.SortOrder;
import com.repo.student.StudentRepository.Model.Student;
import com.repo.student.StudentRepository.Service.StudentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.UpdateOptions;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.cassandra.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Created by AdarshSrinivasan
 */
@Service
public class StudentServiceImpl implements StudentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

  @Autowired
  private CassandraTemplate cassandraTemplate;

  public void createStudentRecord(Student student){
    LOGGER.info("Insert Student : {}", student.getRoll());
    student.setYear(2018); //Constant value across all records
    cassandraTemplate.insert(student);
  }

  public void createStudentRecordBulk(List<Student> students){
    students.forEach(this::createStudentRecord);
  }

  public List<Student> getAllStudent(Integer pageNum , int recordsPerPage, String sortedBy,
      SortOrder sortOrder){
    String tableName ;
    switch (sortedBy){
      case "roll":
      case "" : tableName = (sortOrder == SortOrder.ASC) ? "student_by_roll_ASC" :
          "student_by_roll_DESC"; break;
      case "firstName" : tableName = (sortOrder == SortOrder.ASC) ? "student_by_first_name_ASC" :
        "student_by_first_name_DESC"; break;
      case "lastName" : tableName = (sortOrder == SortOrder.ASC) ? "student_by_last_name_ASC" :
          "student_by_last_name_DESC"; break;
      case "department" : tableName = (sortOrder == SortOrder.ASC) ? "student_by_department_ASC" :
          "student_by_department_DESC"; break;
      case "club" : tableName = (sortOrder == SortOrder.ASC) ? "student_by_club_ASC" :
          "student_by_club_DESC"; break;
      default: throw new InvalidDataException("Invalid/Unrecognized table");
    }
    if(pageNum < 1 || recordsPerPage < 0){
      throw new InvalidDataException("Pagination value should be > 0");
    }

    String fetchQuery = "SELECT * FROM " + tableName + " LIMIT " + recordsPerPage * pageNum + ";";

    LOGGER.info("Getting all students Students by : {}", fetchQuery);
    List<Student> resultStudents = cassandraTemplate.select(fetchQuery, Student.class);
    if(resultStudents.isEmpty()){
      return resultStudents;
    }

    return resultStudents.subList(resultStudents.size() - recordsPerPage,
        (resultStudents.size() - 1));
  }

  public Student getAStudent(String department, Long roll){
    Where select = QueryBuilder
        .select()
        .from("student")
        .where(QueryBuilder.eq("department", department))
        .and(QueryBuilder.eq("roll", roll));
    LOGGER.info("Getting a students Student by : {}", select.toString());
    return cassandraTemplate.selectOne(select, Student.class);
  }

  public void updateStudentRecord(Student student){
    Student storedStudent = getAStudent(student.getDepartment(), student.getRoll());
    if(storedStudent == null){
      throw new StudentNotFound("No student found for the given request");
    }
    LOGGER.info("Updating student : {}", student.getRoll());
    Query query = Query
        .query(
            Criteria
                .where("department")
                .is(student.getDepartment())
        ).and(
            Criteria
                .where("roll")
                .is(student.getRoll())
        ).and(
            Criteria
                .where("year")
                .is(student.getYear())
        );
    Update update = null;
    if(!storedStudent.getClub().equals(student.getClub())){
      update = Update.update("club", student.getClub());
    }
    if(!storedStudent.getFirstName().equals(student.getFirstName())){
      if(update == null){
        update = Update.update("first_name", student.getFirstName());
      }
      else {
        update.set("first_name", student.getFirstName());
      }
    }
    if(!storedStudent.getLastName().equals(storedStudent.getLastName())){
      if(update == null){
        update = Update.update("last_name", student.getLastName());
      }
      else {
        update.set("last_name", student.getLastName());
      }
    }
    if(!storedStudent.getYear().equals(student.getYear())){
      throw new InvalidDataException("Invalid Update on field \"year\"");
    }
    if(update == null){
      LOGGER.info("Nothing to Update, stored data and request data are same");
      return;
    }
    cassandraTemplate.update(query, update, Student.class);
  }

  public void deleteAStudent(String department, Long roll){
    Query query =
        Query.query(Criteria.where("department").is(department)).and(Criteria.where("roll").is(roll));
    LOGGER.info("Deleting a students Student by : {}", query.toString());
    cassandraTemplate.delete(query, Student.class);
  }

  public void deleteAllStudents() {
    LOGGER.info("Deleting all students");
    cassandraTemplate.truncate(Student.class);
  }
}
