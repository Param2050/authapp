package com.temp.authapp.facade;

import com.temp.authapp.exception.DuplicateUserException;
import com.temp.authapp.exception.ResourceNotFoundException;
import com.temp.authapp.exception.ValidationException;
import com.temp.authapp.facade.UserFacade;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.service.UserService;
import com.temp.authapp.util.CustomPasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.temp.authapp.stubs.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static com.temp.authapp.util.Constants.INVALID_USER_NAME;


@SpringBootTest
class UserFacadeTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomPasswordEncoder customPasswordEncoder;

	private User user;


	@BeforeEach
	public void init() {
		user = new User();
	}

	@Test
	public void testValidateUserDetails() {
		UserRequestDto userRequestDto = new UserRequestDto(Stub.EMPTY_USERNAME, Stub.PASSWORD);
		Assertions.assertThrows(ValidationException.class, ()->
				userFacade.validateUserDetails(userRequestDto));
	}

	@Test
	public void testCreateUser() {
		user = Stub.getUser(user);
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Assertions.assertEquals(Stub.expectedResponse(user), userFacade.getUserResponseDto(user));
	}

	@Test
	public void testUserAuthenticate() {
		user = Stub.getUser(user);
		Mockito.when(userRepository.save(user)).thenReturn(user);
		User baseUser = new User(Stub.USERNAME, Stub.PASSWORD);
		Assertions.assertEquals(true, userFacade.authenticate(user, baseUser));
	}

}
