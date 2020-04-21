package com.temp.authapp.facade;

import com.temp.authapp.facade.UserFacade;
import com.temp.authapp.model.User;
import com.temp.authapp.model.UserResponseDto;
import com.temp.authapp.repository.UserRepository;
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
	private CustomPasswordEncoder customPasswordEncoder;

	@Test
	public void createUserTest() {
		User user = new User(UUID.randomUUID(), Stub.username, customPasswordEncoder.encode(Stub.password));
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Assertions.assertEquals(Stub.expectedResponse(user), userFacade.getUserResponseDto(user));
	}

}
