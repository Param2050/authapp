package com.temp.authapp.facade;

import com.temp.authapp.exception.DuplicateUserException;
import com.temp.authapp.facade.UserFacade;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserRequestDto;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.repository.UserRepository;
import com.temp.authapp.service.UserService;
import com.temp.authapp.util.CustomPasswordEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.temp.authapp.stubs.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.UUID;


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

	@Test
	public void testCreateUser() {
		User user = new User(UUID.randomUUID(), "Rahul", customPasswordEncoder.encode("rahul123"));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Assertions.assertEquals(Stub.expectedResponse(user), userFacade.getUserResponseDto(user));
	}

	@Test
	public void testUserAuthenticate() {
		User persisteduser = new User(UUID.randomUUID(), "Nitin", customPasswordEncoder.encode("nitin123"));
		Mockito.when(userRepository.save(persisteduser)).thenReturn(persisteduser);
		User baseUser = new User("Nitin", "nitin123");
		Assertions.assertEquals(true, userFacade.authenticate(persisteduser, baseUser));
	}


}
