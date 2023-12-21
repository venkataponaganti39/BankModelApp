package com.minna.bma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.UUID;

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
	public void getBankAccountsByTransactionAmount() throws Exception {
		BankAccount account = new BankAccount();
		account.setIdentifier(UUID.randomUUID());
		account.setUserIdentifier(UUID.randomUUID());

		HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
		ResponseEntity<BankAccount> response = restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
				HttpMethod.POST, request, BankAccount.class);

		Transaction transaction = new Transaction();
		transaction.setAmount(-400);
		transaction.setBankAccount(new BankAccount(response.getBody().getUUID()));
		transaction.setDate(LocalDateTime.now());
		transaction.setReceiptNumber("12345");
		transaction.setText("Gym");

		HttpEntity<Transaction> req = new HttpEntity<>(transaction, headers);
		restTemplate.exchange(createURLWithPort("/api/bank-accounts/" + response.getBody().getUUID() + "/transactions"),
				HttpMethod.POST, req, Transaction.class);

		mockMvc.perform(MockMvcRequestBuilders.get(createURLWithPort("/api/bank-accounts"))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

	}

	@Test
	public void testCreateBankAccount() {
		BankAccount account = new BankAccount();
		account.setIdentifier(UUID.randomUUID());
		account.setUserIdentifier(UUID.randomUUID());

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
				MockMvcRequestBuilders.get("/api/bank-accounts/{UUID}", 1).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testUpdateBankAccount() throws Exception {
		BankAccount account = new BankAccount();
		account.setIdentifier(UUID.randomUUID());
		account.setUserIdentifier(UUID.randomUUID());

		HttpEntity<BankAccount> request = new HttpEntity<>(account, headers);
		ResponseEntity<BankAccount> response = restTemplate.exchange(createURLWithPort("/api/bank-accounts"),
				HttpMethod.POST, request, BankAccount.class);
		ResponseEntity<BankAccount> res = restTemplate.exchange(
				createURLWithPort("/api/bank-accounts/" + response.getBody().getUUID()), HttpMethod.PUT, request,
				BankAccount.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/bank-accounts/" + response.getBody().getUUID())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
		assertEquals(HttpStatus.OK, res.getStatusCode());
		assertEquals("identifier1", res.getBody().getIdentifier());
		assertEquals("userIdentifier1", res.getBody().getUserIdentifier());
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:8080" + uri;
	}

}
