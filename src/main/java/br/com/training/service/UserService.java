package br.com.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Transactional
	public UserResponse createUser(UserForm form) {
		return repository.save(form.toEntity()).toResponse();
	}
	
	@Transactional(readOnly = true)
	public UserResponse getUser(String cpf) {
		return repository.findByCpf(cpf).toResponse();
	}
	
	@Transactional
	public UserResponse updateUser(String cpf, UserForm form) {
		User entity = repository.findByCpf(cpf);
		updateData(entity, form);
		return repository.save(entity).toResponse();
	}
	
	private void updateData(User entity, UserForm form) {
		entity.setName(form.getName());
		entity.setEmail(form.getEmail());
		entity.setBirthDate(form.getBirthDate());
	}
	
	@Transactional
	public void deleteUser(String cpf) {
		repository.delete(repository.findByCpf(cpf));
	}
}
