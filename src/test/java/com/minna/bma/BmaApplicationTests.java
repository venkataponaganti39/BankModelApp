package com.minna.bma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minna.bma.entity.BankAccount;
import com.minna.bma.entity.Transaction;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BmaApplicationTests {

	private TestRestTemplate restTemplate;
	private HttpHeaders headers;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		restTemplate = new TestRestTemplate();
		headers = new HttpHeaders();
	}

	@Test
	public void testCreateBankAccount() {
		BankAccount account = new BankAccount();
		account.setIdentifier("identifier");
		account.setUserIdentifier("userIdentifier");

		HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
		ResponseEntity<BankAccount> response = restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
				HttpMethod.POST, request, BankAccount.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("identifier", response.getBody().getIdentifier());
		assertEquals("userIdentifier", response.getBody().getUserIdentifier());
	}

	@Test
	public void testRetrieveAllBankAccounts() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/bank-accounts").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testRetrieveBankAccountById() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/bank-accounts/{id}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testUpdateBankAccount() throws Exception {
		BankAccount account = new BankAccount();
		account.setIdentifier("identifier1");
		account.setUserIdentifier("userIdentifier1");

		HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
		ResponseEntity<BankAccount> response = restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
				HttpMethod.POST, request, BankAccount.class);
		ResponseEntity<BankAccount> res = restTemplate.exchange(createURLWithPort("/api/bank-accounts/"+response.getBody().getId()),
				HttpMethod.PUT, request, BankAccount.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/bank-accounts/"+response.getBody().getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("identifier1", res.getBody().getIdentifier());
		assertEquals("userIdentifier1", res.getBody().getUserIdentifier());
	}
	
	/*
	 * @Test public void tetCreateTransaction() { BankAccount account = new
	 * BankAccount(); account.setIdentifier("identifier");
	 * account.setUserIdentifier("userIdentifier");
	 * 
	 * HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
	 * ResponseEntity<BankAccount> response =
	 * restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
	 * HttpMethod.POST, request, BankAccount.class);
	 * 
	 * Transaction transaction = new Transaction(); transaction.setAmount(-100.0);
	 * transaction.setReceiptNumber("1234"); transaction.setText("Gym");
	 * 
	 * HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
	 * ResponseEntity<BankAccount> response =
	 * restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
	 * HttpMethod.POST, request, BankAccount.class);
	 * 
	 * assertEquals(HttpStatus.CREATED, response.getStatusCode());
	 * assertEquals("identifier", response.getBody().getIdentifier());
	 * assertEquals("userIdentifier", response.getBody().getUserIdentifier()); }
	 */

	private String createURLWithPort(String uri) {
		return "http://localhost:8080" + uri;
	}

}
