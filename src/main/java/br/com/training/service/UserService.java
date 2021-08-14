package br.com.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.training.dto.UserForm;
import br.com.training.dto.UserResponse;
import br.com.training.model.User;
import br.com.training.repository.UserRepository;
import br.com.training.service.exceptions.DatabaseException;
import br.com.training.service.exceptions.ResourceNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	@Transactional
	public UserResponse createUser(UserForm form) {
		try {
			return repository.save(form.toEntity()).toResponse();
		} catch(DataIntegrityViolationException e) {
			throw new DatabaseException("CPF já cadastrado!");
		}
	}
	
	@Transactional(readOnly = true)
	public UserResponse getUser(String cpf) {
		try {
			return repository.findByCpf(cpf).toResponse();
		} catch (NullPointerException e) {
			throw new ResourceNotFoundException("Usuário com CPF " + cpf + " não encontrado!");
		}
	}
	
	@Transactional
	public UserResponse updateUser(String cpf, UserForm form) {
		User entity = repository.findByCpf(cpf);
		
		if(entity == null) {
			throw new ResourceNotFoundException("Usuário com CPF " + cpf + " não encontrado!");
		}
		
		updateData(entity, form);
		return repository.save(entity).toResponse();
	}
	
	@Transactional
	public void deleteUser(String cpf) {
		try {
			repository.delete(repository.findByCpf(cpf));
		} catch(InvalidDataAccessApiUsageException e) {
			throw new ResourceNotFoundException("CPF não encontrado " + cpf);
		}
	}
	
	private void updateData(User entity, UserForm form) {
		entity.setName(form.getName());
		entity.setEmail(form.getEmail());
		entity.setBirthDate(form.getBirthDate());
	}
}
