package com.temp.authapp.service;

import com.temp.authapp.facade.UserFacade;
import com.temp.authapp.model.AuthenticationResponse;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.util.CustomPasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.temp.authapp.stubs.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;


@SpringBootTest
class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;

	@Test
	public void createUserTest() {
		User user = getUser();
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Assertions.assertEquals(expectedResponse(user), userFacade.getUserResponseDto(user));
	}

	private User getUser() {
		User user = new User();
		user.setId(UUID.randomUUID());
		user.setUsername(Stub.username);
		user.setPassword(customPasswordEncoder.encode(Stub.password));
		return user;
	}

	private UserResponseDto expectedResponse(User user) {
		return new UserResponseDto(user.getUsername());
	}

}
