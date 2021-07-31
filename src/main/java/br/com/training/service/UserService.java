package br.com.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.training.model.User;
import br.com.training.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Transactional
	public User createUser(User user) {
		return repository.save(user);
	}
	
	@Transactional(readOnly = true)
	public User getUser(String cpf) {
		return repository.findByCpf(cpf);
	}
	
	@Transactional
	public User updateUser(String cpf, User updated) {
		User entity = repository.findByCpf(cpf);
		updateData(entity, updated);
		return repository.save(entity);
	}
	
	private void updateData(User entity, User updated) {
		entity.setName(updated.getName());
		entity.setEmail(updated.getEmail());
		entity.setBirthDate(updated.getBirthDate());
	}
	
	@Transactional
	public void deleteUser(String cpf) {
		repository.delete(repository.findByCpf(cpf));
	}
}
