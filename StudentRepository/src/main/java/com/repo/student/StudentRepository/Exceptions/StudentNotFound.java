package com.repo.student.StudentRepository.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by asriniva
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentNotFound extends RuntimeException {

  public StudentNotFound() {
  }

  public StudentNotFound(String s) {
    super(s);
  }

  public StudentNotFound(String s, Throwable throwable) {
    super(s, throwable);
  }

  public StudentNotFound(Throwable throwable) {
    super(throwable);
  }

  public StudentNotFound(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }
}
