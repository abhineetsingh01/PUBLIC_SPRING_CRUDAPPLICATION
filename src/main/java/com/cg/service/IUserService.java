package com.cg.service;

import com.cg.dto.UserDTO;
import com.cg.dto.WalletDTO;
import com.cg.entity.User;
import com.cg.response.ApiResponse;

public interface IUserService {

	ApiResponse<User> registerUser(UserDTO userDTO);

	ApiResponse<Double> getBalance(Integer id);

	ApiResponse<String> addAmount(WalletDTO walletDTO);

	ApiResponse<String> transferFunds(Integer fromWalletId, Integer toWalletId, Double amount);

}
