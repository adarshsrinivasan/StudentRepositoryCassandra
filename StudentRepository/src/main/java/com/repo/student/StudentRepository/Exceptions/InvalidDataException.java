package com.repo.student.StudentRepository.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by asriniva
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {

  public InvalidDataException() {
  }

  public InvalidDataException(String s) {
    super(s);
  }

  public InvalidDataException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public InvalidDataException(Throwable throwable) {
    super(throwable);
  }

  public InvalidDataException(String s, Throwable throwable, boolean b, boolean b1) {
    super(s, throwable, b, b1);
  }
}
