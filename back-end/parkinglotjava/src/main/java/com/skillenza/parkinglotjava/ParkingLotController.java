package com.skillenza.parkinglotjava;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class ParkingLotController {

	@Autowired
	private ParkingLotRepository parkingLotRepository;

	@GetMapping("/parkings")
	public List<ParkingLot> getAllParkingLots() {
		return parkingLotRepository.findAll();
	}

	@PostMapping("/parkings")
	public ResponseEntity createParkingLot(@Valid @RequestBody ParkingLot parkingLot) {
		ParkingLot savedParkingLot = parkingLotRepository.save(parkingLot);
		return ResponseEntity.status(HttpStatus.OK).body(savedParkingLot);
	}

	@DeleteMapping("/parkings/{id}")
	public ResponseEntity removeParkingLot(@PathVariable long id) {
		parkingLotRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("PARKING DETAILS REMOVED");
	}
}
