package com.n26.codechallenge.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.codechallenge.dto.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatisticsControllerTests {

	@Autowired
    private MockMvc mockMvc;
	
	
	/**
     * Tests initial statistics. Should be empty
     *
     * @throws Exception
     */
	@Test
    public void testA() throws Exception {
        mockMvc.perform(get("/statistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count", equalTo(0)))
            .andExpect(jsonPath("$.min", equalTo(0.0)));
    }

	/**
     * Adds a valid transaction with current timestamp.
     *
     * @throws Exception
     */
	@Test
    public void testB() throws Exception {
		mockMvc.perform(post("/transaction")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(asJsonString(new Transaction(Instant.now().toEpochMilli(), 100.0))))
	        .andExpect(status().isCreated());
    }
	
	/**
     * Adds a valid transaction with current timestamp for statistic matters.
     *
     * @throws Exception
     */
	@Test
    public void testC() throws Exception {
		mockMvc.perform(post("/transaction")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(asJsonString(new Transaction(Instant.now().toEpochMilli() + 30000, 50.0))))
	        .andExpect(status().isCreated());
    }
	
	/**
     * Retrieves updated statistics.
     *
     * @throws Exception
     */
	@Test
    public void testD() throws Exception {
        mockMvc.perform(get("/statistics"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.count", equalTo(2)))
            .andExpect(jsonPath("$.min", equalTo(50.0)))
            .andExpect(jsonPath("$.max", equalTo(100.0)))
            .andExpect(jsonPath("$.avg", equalTo(75.0)))
            .andExpect(jsonPath("$.sum", equalTo(150.0)));
    }
	

	/**
     * Tries to add transaction with timestamp older than 60s.
     *
     * @throws Exception
     */
	@Test
    public void testE() throws Exception {
        mockMvc.perform(post("/transaction")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new Transaction(Instant.now().toEpochMilli() - 60001 , 10.0))))
            .andExpect(status().isNoContent());
    }

	/**
     * Tries to add transaction with current timestamp but constraint violation.
     *
     * @throws Exception
     */
	@Test
    public void testF() throws Exception {
        mockMvc.perform(post("/transaction")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(new Transaction(Instant.now().toEpochMilli(), null))))
            .andExpect(status().isBadRequest());
    }
	
	/**
     * Waits one minute to make sure transactions older than 60s are not being considered for statistics.
     *
     * @throws Exception
     */
	@Test
    public void testG() throws Exception {
		System.out.println("Please wait for 1 minute to make sure statistics are being properly collected...");
		Thread.sleep(60001);
		mockMvc.perform(get("/statistics"))
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.count", equalTo(1)))
	        .andExpect(jsonPath("$.min", equalTo(50.0)))
	        .andExpect(jsonPath("$.max", equalTo(50.0)))
	        .andExpect(jsonPath("$.avg", equalTo(50.0)))
	        .andExpect(jsonPath("$.sum", equalTo(50.0)));
    }
    
	
    /**
     * Transform any POJO to String representing a JSON Object
     *
     * @param obj Any POJO
     * @return String representing the input object in JSON
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
