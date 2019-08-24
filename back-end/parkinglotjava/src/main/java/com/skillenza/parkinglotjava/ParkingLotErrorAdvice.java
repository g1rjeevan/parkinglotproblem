package com.skillenza.parkinglotjava;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ParkingLotErrorAdvice {

	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<ParkingLotApiError> handleRunTimeException(RuntimeException e) {
		return buildResponseEntity(new ParkingLotApiError(HttpStatus.INTERNAL_SERVER_ERROR, e));
	}

	@ExceptionHandler({ DataIntegrityViolationException.class })
	public ResponseEntity<ParkingLotApiError> handleSQLException(DataIntegrityViolationException e) {
		return buildResponseEntity(
				new ParkingLotApiError(HttpStatus.CONFLICT, "VEHICLE ALREADY PARKED", e));
	}

	private ResponseEntity<ParkingLotApiError> buildResponseEntity(ParkingLotApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
