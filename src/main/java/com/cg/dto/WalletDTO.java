package com.cg.dto;

public class WalletDTO {
	private String emailId;
	private Double amount;
   
	public WalletDTO(String emailId, Double amount) {
		super();
		this.emailId = emailId;
		this.amount = amount;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
