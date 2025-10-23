package com.cg.request;

public class TransferRequest {
	private Integer walletId;
	private Integer toWalletId;
	private Double balance;
	public TransferRequest(Integer walletId, Integer toWalletId, Double balance) {
		super();
		this.walletId = walletId;
		this.toWalletId = toWalletId;
		this.balance = balance;
	}
	public Integer getWalletId() {
		return walletId;
	}
	public void setWalletId(Integer walletId) {
		this.walletId = walletId;
	}
	public Integer getToWalletId() {
		return toWalletId;
	}
	public void setToWalletId(Integer toWalletId) {
		this.toWalletId = toWalletId;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
	 

}
