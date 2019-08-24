package com.skillenza.parkinglotjava;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkinglotjavaApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ParkingLotRepository parkingLotRepository;

	@Test
	public void getAllParkingsLot() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/parkings")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void createParkingLot() throws Exception {
		mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/parkings")
				.content(asJsonString(getParkingLot())).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is(415));
	}

	@Test
	public void deleteParkingLot() throws Exception {;
		mvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/parkings/25092515")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()); 
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final ParkingLot getParkingLot() {
		ParkingLot lot = new ParkingLot();
		lot.setLot(2509);
		lot.setVehicleNumber(2515);
		lot.setParkingAmount(20);
		lot.setParkingDuration(60);
		lot.setId(25092515);
		return lot;
	}

	// your test goes here

}
