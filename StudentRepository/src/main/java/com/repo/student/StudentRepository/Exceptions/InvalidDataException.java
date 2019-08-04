package com.repo.student.StudentRepository.Exceptions;

/**
 * Created by asriniva
 */
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
