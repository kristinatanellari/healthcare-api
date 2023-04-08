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

	@ExceptionHandler(value = PaginationException.class)
	protected ResponseEntity<Object> handlePaginationException(PaginationException paginationException) {
		long code = System.currentTimeMillis();
		String message = "code:" + code + " " + paginationException.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
