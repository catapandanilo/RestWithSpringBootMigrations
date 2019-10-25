package br.com.catapan.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.catapan.adapter.DozerConverter;
import br.com.catapan.data.model.Person;
import br.com.catapan.data.vo.v1.PersonVO;
import br.com.catapan.exception.ResourceNotFoundException;
import br.com.catapan.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository repository;
	
	public PersonVO create(PersonVO person) {
		Person entity = DozerConverter.parseObject(person, Person.class);
		PersonVO vo = DozerConverter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
	
	public List<PersonVO> findAll() {
		return DozerConverter.parseListObjects(repository.findAll(), PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}
	
	public PersonVO update(PersonVO person) {
		Person entity = repository.findById(person.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());

		return DozerConverter.parseObject(repository.save(entity), PersonVO.class);
	}	
	
	public void delete(Long id) {
		Person entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
	}

}
