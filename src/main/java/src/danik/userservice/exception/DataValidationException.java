package src.danik.userservice.exception;

public class DataValidationException extends RuntimeException{
  public DataValidationException(String message) {
    super(message);
  }
}
