package br.com.training.tests.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.service.UserService;
import br.com.training.service.exceptions.ResourceNotFoundException;
import br.com.training.tests.factory.UserFactory;

@ExtendWith(SpringExtension.class)
public class UserServiceTests {

	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	private String existingCpf;
	private String nonExistingCpf;
	private User user;
	
	@BeforeEach
	void setUp() throws Exception {
		existingCpf = "12345678901";
		nonExistingCpf = "00000000000";
		user = UserFactory.createUser(1L);
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(user);
		
		Mockito.when(repository.findByCpf(existingCpf)).thenReturn(user);
		Mockito.when(repository.findByCpf(nonExistingCpf)).thenReturn(user);
		
		Mockito.doNothing().when(repository).delete(user);
		Mockito.doThrow(ResourceNotFoundException.class).when(repository).delete(null);
		
		Mockito.when(repository.findByCpf(nonExistingCpf)).thenReturn(null);
		Mockito.when(repository.findByCpf(existingCpf)).thenReturn(user);
	}
	
	@Test
	public void getUserShouldThrowResourceNotFoundExceptionWhenCpfDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.getUser(nonExistingCpf);
		});
	}
	
	@Test
	public void getUserShouldReturnUserResponseWhenCpfExists() {
		UserResponse response = service.getUser(existingCpf);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponse.class, response.getClass());
	}
	
	@Test
	public void updateUserShouldThrowResourceNotFoundExceptionWhenCpfDoesNotExists() {
		UserForm form = UserFactory.createUserForm();
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.updateUser(nonExistingCpf, form);
		});
	}
	
	@Test
	public void updateUserShouldReturnUserResponseWhenCpfExists() {
		UserForm form = UserFactory.createUserForm();
		UserResponse response = service.updateUser(existingCpf, form);
		
		Assertions.assertNotNull(response);
		Assertions.assertEquals(UserResponse.class, response.getClass());
		
	}
	
	@Test
	public void deleteUserShouldThrowResourceNotFoundExceptionWhenCpfDoesNotExists() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteUser(nonExistingCpf);
		});
		
		Mockito.verify(repository, Mockito.times(1)).delete(null);
	}
	
	@Test
	public void deleteUserShouldReturnDoNothingWhenCpfExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.deleteUser(existingCpf);
		});
		
		Mockito.verify(repository, Mockito.times(1)).delete(user);
	}
}
