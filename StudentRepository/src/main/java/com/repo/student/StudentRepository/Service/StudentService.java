package com.repo.student.StudentRepository.Service;

import com.repo.student.StudentRepository.Model.SortOrder;
import com.repo.student.StudentRepository.Model.Student;
import java.util.List;

/**
 * Created by AdarshSrinivasan
 */
public interface StudentService {

  public void createStudentRecord(Student student);

  public void createStudentRecordBulk(List<Student> students);

  public List<Student> getAllStudent(Integer pageNum , int recordsPerPage, String sortedBy,SortOrder sortOrder);

  public Student getAStudent(String department, Long roll);

  public void updateStudentRecord(Student student);

  public void deleteAStudent(String department, Long roll);

  void deleteAllStudents();
}
