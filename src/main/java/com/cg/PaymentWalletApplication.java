package com.cg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cg.entity.Currency;
import com.cg.repository.CurrencyRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class PaymentWalletApplication {

	@Autowired
	private CurrencyRepository currencyRepo;

	public static void main(String[] args) {
		SpringApplication.run(PaymentWalletApplication.class, args);
	}

	@PostConstruct
	public void addCurrency() {
		if (currencyRepo.count() == 0) {
			Currency inr = new Currency();
			inr.setName("Indian Rupee");
			inr.setAbbreviation("INR");

			/*
			 * Currency usd = new Currency(); usd.setName("US Dollar");
			 * usd.setAbbreviation("USD");
			 */

			// currencyRepo.saveAll(List.of(inr, usd));

			currencyRepo.save(inr);
			System.out.println("Currency data initialized.");

		}

	}

}
