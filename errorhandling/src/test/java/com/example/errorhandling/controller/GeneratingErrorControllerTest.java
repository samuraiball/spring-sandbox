package com.example.errorhandling.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class GeneratingErrorControllerTest {

	private MockMvc mockMvc;

	@BeforeEach
	void setUp(){
		mockMvc = MockMvcBuilders.standaloneSetup(GeneratingErrorController.class).build();

	}

	@Test
	void InternalServerError() throws Exception{
		//TODO
	}

	@Test
	void NoHandler() throws Exception{
		mockMvc.perform(get("/helloNoHandler"))
				.andExpect(status().isNotFound());
	}
}