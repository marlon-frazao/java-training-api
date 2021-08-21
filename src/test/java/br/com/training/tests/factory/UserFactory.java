package br.com.training.tests.factory;

import java.time.LocalDate;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.model.User;

public class UserFactory {

	public static User createUser() {
		return new User(null, "João da Silva", "jsilva@hotmail.com", "12345678901", LocalDate.of(1980, 3, 21));
	}
	
	public static User createUser(Long id) {
		User user = createUser();
		user.setId(id);
		return user;
	}
	
	public static UserForm createUserForm() {
		return new UserForm("Pedrita das Candongas", "p.candongas@gmail.com", "16325123015", LocalDate.of(1996, 4, 15));
	}
	
	public static UserResponse createUserResponse() {
		return new UserResponse(createUser(1L));
	}
}
