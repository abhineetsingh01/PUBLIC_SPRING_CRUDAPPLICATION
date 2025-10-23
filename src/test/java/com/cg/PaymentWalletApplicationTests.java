package com.cg;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cg.dto.WalletDTO;
import com.cg.entity.User;
import com.cg.entity.Wallet;
import com.cg.repository.UserRepository;
import com.cg.repository.WalletRepository;
import com.cg.response.ApiResponse;
import com.cg.service.impl.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaymentWalletApplicationTests {

	@InjectMocks
	private UserServiceImpl walletService;
	@Mock
	private UserRepository userRepo;
	@Mock
	private WalletRepository walletRepo;

	@Test
	void contextLoads() {

		WalletDTO dto = new WalletDTO("", 50.0);

		User user = new User();
		Wallet wallet = new Wallet();
		wallet.setBalance(100.0);
		user.setWallet(wallet);

		when(userRepo.findById(1)).thenReturn(Optional.of(user));
		when(walletRepo.save(any(Wallet.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		ApiResponse<String> updated = walletService.addAmount(dto);

		// Assert
		// assertEquals(150.0, updated.getBalance());

	}

}
