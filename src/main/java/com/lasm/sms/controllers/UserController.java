package com.lasm.sms.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lasm.sms.requests.UserCreationRequest;
import com.lasm.sms.responses.UserCreationResponse;
import com.lasm.sms.responses.errors.ErrorMessages;
import com.lasm.sms.services.UserService;
import com.lasm.sms.shared.dto.UserDto;



@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserCreationResponse> createUser(@RequestBody @Valid UserCreationRequest userRequest) throws Exception {
		if (userRequest.getFirstName().isEmpty() || userRequest.getLastName().isEmpty()
				|| userRequest.getEmail().isEmpty() || userRequest.getPassword().isEmpty()
				|| userRequest.getRole().isEmpty())
			throw new Exception(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);
		UserDto createdUser = userService.createUser(userDto);
		UserCreationResponse returnedValue = new UserCreationResponse();
		BeanUtils.copyProperties(createdUser, returnedValue);
		return new ResponseEntity<UserCreationResponse>(returnedValue, HttpStatus.CREATED);
	}

}
