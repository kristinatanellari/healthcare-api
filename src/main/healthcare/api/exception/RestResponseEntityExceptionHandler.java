package main.healthcare.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ValidateDataException.class)
	protected ResponseEntity<Object> handleDataValidationException(ValidateDataException validateDataException) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + validateDataException.getMessage();
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NotArticleAuthorException.class)
	protected ResponseEntity<Object> handleNotArticleAuthorException(NotArticleAuthorException notArticleAuthorException) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + notArticleAuthorException.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NotFoundData.class)
	protected ResponseEntity<Object> handleNotFoundDataException(NotFoundData notFoundData) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + notFoundData.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = UnavailableDoctors.class)
	protected ResponseEntity<Object> handleUnavailableDoctorsException(UnavailableDoctors unavailableDoctors) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + unavailableDoctors.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + userNotFoundException.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
