package com.repo.student.StudentRepository.Controller.v1;

import com.repo.student.StudentRepository.Model.SortOrder;
import com.repo.student.StudentRepository.Model.Student;
import com.repo.student.StudentRepository.Service.StudentService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by AdarshSrinivasan
 */
@RestController
@RequestMapping("/api/v1/student")
@Slf4j
public class StudentRepoController {

  @Autowired
  private StudentService studentService;

  @PostMapping
  public void addStudentRecord(@RequestBody Student student){
    studentService.createStudentRecord(student);
  }

  @PostMapping("/bulk")
  public void addStudentRecordBulk(@RequestBody List<Student> students){
    studentService.createStudentRecordBulk(students);
  }

  @GetMapping
  public List<Student> getAllStudents(@RequestParam(defaultValue = "10", required = false) int recordsPerPage,
      @RequestParam(defaultValue = "", required = false) String sortedBy,
      @RequestParam(defaultValue = "1", required = false) Integer pageNum,
      @RequestParam(defaultValue = "ASC", required = false) SortOrder sortOrder){
    return studentService.getAllStudent(pageNum, recordsPerPage, sortedBy, sortOrder);
  }

  @GetMapping("/{department}/{roll}")
  public Student getAStudent(@PathVariable String department, @PathVariable Long roll){
    return studentService.getAStudent(department, roll);
  }

  @PutMapping
  public void updateStudent(@RequestBody Student student){
    studentService.updateStudentRecord(student);
  }

  @DeleteMapping("/{department}/{roll}")
  public void deleteAStudent(@PathVariable String department, @PathVariable Long roll){
    studentService.deleteAStudent(department, roll);
  }

  @DeleteMapping
  public void deleteAllStudents(){
    studentService.deleteAllStudents();
  }

}
