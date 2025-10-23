package com.cg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.UserDTO;
import com.cg.dto.WalletDTO;
import com.cg.entity.User;
import com.cg.repository.UserRepository;
import com.cg.request.TransferRequest;
import com.cg.response.ApiErrorResponse;
import com.cg.response.ApiResponse;
import com.cg.service.IUserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/users")

public class WalletController {

	@Autowired
	private IUserService userService;

	@Autowired
	private UserRepository userRepo;

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EntityModel<ApiResponse<User>>> register(@Valid @RequestBody UserDTO dto) {
		if (userRepo.findByEmailId(dto.getEmailId()).isPresent()) {

			ApiErrorResponse response = new ApiErrorResponse();

			response.setHttpStatus(HttpStatus.CONFLICT);
			response.setHttpStatusValue(HttpStatus.CONFLICT.value());
			response.setMessage("User with given email id already exists");

			return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).build();
		} else {
			userService.registerUser(dto);
			EntityModel<ApiResponse<User>> resource = EntityModel
					.of(new ApiResponse<>("Wallet account created successfully"));
			resource.add(WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder.methodOn(WalletController.class).getBalance(dto.getId())).withSelfRel());

			return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(resource);
		}
	}

	@PostMapping("/wallet")
	public ResponseEntity<ApiResponse<String>> addAmount(@RequestBody WalletDTO dto) {
		return ResponseEntity.ok(userService.addAmount(dto));
	}

	@GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<Double>> getBalance(@PathVariable("userId") Integer id) {
		if (!String.valueOf(id).matches("\\d")) {
			ApiErrorResponse error = new ApiErrorResponse();
			error.setHttpStatus(HttpStatus.BAD_REQUEST);
			error.setHttpStatusValue(HttpStatus.BAD_REQUEST.value());
			error.setMessage("User id cannot contain characters");
		}

		ApiResponse<Double> balance = userService.getBalance(id);
		return new ResponseEntity<ApiResponse<Double>>(balance, HttpStatus.OK);
	}

	@PostMapping(value = "wallet/transfer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponse<String>> transfer(@RequestBody TransferRequest request,
			@RequestParam Double amount) {
		return ResponseEntity.ok(userService.transferFunds(request.getWalletId(), request.getToWalletId(), amount));
	}

}
