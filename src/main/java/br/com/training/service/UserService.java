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
}
