package com.cg.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cg.dto.UserDTO;
import com.cg.dto.WalletDTO;
import com.cg.entity.Currency;
import com.cg.entity.Transaction;
import com.cg.entity.User;
import com.cg.entity.Wallet;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.ResourceNotFoundException;
import com.cg.exception.UserAlreadyExistsException;
import com.cg.repository.CurrencyRepository;
import com.cg.repository.TransactionRepository;
import com.cg.repository.UserRepository;
import com.cg.repository.WalletRepository;
import com.cg.response.ApiResponse;
import com.cg.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CurrencyRepository currencyRepo;

	@Autowired
	private WalletRepository walletRepo;

	@Autowired
	private TransactionRepository txnRepo;

	@Override
	public ApiResponse<User> registerUser(UserDTO dto) {
		ApiResponse<User> response = new ApiResponse<>();
		try {
			if (userRepo.findByEmailId(dto.getEmailId()).isPresent()) {
				throw new UserAlreadyExistsException("User with given email id already exists");
			}
			User user = new User();
			user.setId(dto.getId());
			user.setUsername(dto.getUsername());
			user.setPassword(dto.getPassword());
			user.setEmailId(dto.getEmailId());

			Currency currency = currencyRepo.findById(1).orElseThrow(); // Default INR
			Wallet wallet = new Wallet();
			wallet.setUser(user);
			wallet.setCurrency(currency);
			wallet.setBalance(0.0);

			user.setWallet(wallet);
			User saveUser = userRepo.save(user);

			response.setData(saveUser);

			response.setHttpStatus(HttpStatus.CREATED);
			response.setHttpStatusValue(HttpStatus.CREATED.value());
			response.setMessage("Wallet account created successfully");

		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.BAD_REQUEST);
			response.setHttpStatusValue(HttpStatus.BAD_REQUEST.value());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@Override
	public ApiResponse<Double> getBalance(Integer Id) {
		ApiResponse<Double> response = new ApiResponse<>();
		try {
			Wallet wallet = walletRepo.findByUserId(Id)
					.orElseThrow(() -> new ResourceNotFoundException("User not found with given id pls check again"));

			response.setData(wallet.getBalance());
			response.setHttpStatus(HttpStatus.OK);
			response.setHttpStatusValue(HttpStatus.OK.value());
			response.setMessage("Get Balance successfully");

		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setHttpStatusValue(HttpStatus.NOT_FOUND.value());
			response.setMessage("Balance Unsuccessfully Showing Due to Some Reason!!Pls Try Again");
		}
		return response;
	}

	@Override
	public ApiResponse<String> addAmount(WalletDTO dto) {
		ApiResponse<String> response = new ApiResponse<>();

		try {
			Wallet wallet = walletRepo.findByUserEmailId(dto.getEmailId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"There is no user registered with above email id::" + dto.getEmailId()));

			wallet.setBalance(wallet.getBalance() + dto.getAmount());

			Transaction txn = new Transaction();
			txn.setWallet(wallet);
			txn.setAmount(dto.getAmount());
			txn.setStatus("CREDIT");
			txn.setDate(LocalDate.now());
			txn.setTime(LocalTime.now());

			txnRepo.save(txn);
			walletRepo.save(wallet);

			response.setHttpStatus(HttpStatus.OK);
			response.setHttpStatusValue(HttpStatus.OK.value());
			response.setMessage("Balance added successfully");

		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.NOT_FOUND);
			response.setHttpStatusValue(HttpStatus.NOT_FOUND.value());
			response.setMessage("Balance Unsuccessfully Showing Due to Some Reason!!Pls Try Again");
		}
		return response;

	}

	@Override
	public ApiResponse<String> transferFunds(Integer fromWalletId, Integer toWalletId, Double amount) {

		ApiResponse<String> response = new ApiResponse<>();
		try {
			if (fromWalletId.equals(toWalletId)) {
				throw new IllegalArgumentException("Source and destination wallet cannot be the same.");
			}

			Wallet from = walletRepo.findById(fromWalletId)
					.orElseThrow(() -> new ResourceNotFoundException("Sender wallet not found"));
			Wallet to = walletRepo.findById(toWalletId)
					.orElseThrow(() -> new ResourceNotFoundException("Receiver wallet not found"));

			if (from.getBalance() < amount) {
				throw new InsufficientBalanceException("Insufficient balance. You have Rs " + from.getBalance()
						+ " balance in your wallet.Kindly review your balance");
			}

			from.setBalance(from.getBalance() - amount);
			to.setBalance(to.getBalance() + amount);

			Transaction txn = new Transaction();
			txn.setWallet(from);
			txn.setAmount(amount);
			txn.setStatus("DEBIT");
			txn.setDate(LocalDate.now());
			txn.setTime(LocalTime.now());

			txnRepo.save(txn);
			walletRepo.save(from);
			walletRepo.save(to);

			response.setHttpStatus(HttpStatus.OK);
			response.setHttpStatusValue(HttpStatus.OK.value());
			response.setMessage(
					"Transfer done successfully. Use transfer id " + txn.getId() + " for further reference");

		} catch (Exception e) {
			response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setHttpStatusValue(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage("Amount unsuccessfully transferred Due to Some Reason!!Pls Try Again");
		}
		return response;

	}

}
