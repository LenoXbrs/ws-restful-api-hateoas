package br.com.lenox.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

//hateoas
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import org.springframework.stereotype.Service;

import br.com.lenox.controllers.PersonControler;
import br.com.lenox.data.vo.v1.PersonVO;
import br.com.lenox.data.vo.v2.PersonVOV2;
import br.com.lenox.exceptions.ResourceNotFoundException;
import br.com.lenox.mapper.DozerMapper;
import br.com.lenox.mapper.custom.PersonMapper;
import br.com.lenox.model.Person;
import br.com.lenox.repositories.PersonRepository;

//encara como um objeto que vai ser injetado em runtime em outras classes, vai ser injetado com @Autowired
@Service
public class PersonServices {
	
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	
	public List<PersonVO> findAll() {
		logger.info("Finding all persons");
//comentario
	
		var persons = DozerMapper.parseListObject(repository.findAll(), PersonVO.class); 
		//tem que iterar por ser uma lista mas confesso entendi nada
		persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonControler.class).findById(p.getKey())).withSelfRel()));
		
		return persons;
	}
	
	public PersonVO findById(Long id) {
		
		
		logger.info("Finding one person");
		
		//para usar o findbyid tem que lançar uma exceção que nos mesmos fizemos usando aquele padrao la de 2 pacotes
		var entity = repository.findById(id) // pode ser que isso seja depreciado ai usa o getReferenceById qualquer coisa
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		//Fazendo o HATEOAS apartir daki --- tem que fazer um import static especifico, vai ta la nos imports é só procurar o que seja static
				PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
				/*
				 isso coloca um link no json que quando voce clica vai pra no caso findbyid mas eu testei trocando para findall
				 e assim que cliquei no link do json ele trocou a requisição e deixou pronto pra fazer o findall no postman, só tive que 
				 clicar em send;
				 obs: o isso = .add(linkTo(methodOn(PersonControler.class).findById(id)).withSelfRel());
				 */
				 
				vo.add(linkTo(methodOn(PersonControler.class).findById(id)).withSelfRel());
				
		return vo; 
				
	}
	

	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person!");
		//o jpa / banco só reconhece entity entao para podermos salva-lo no banco primeiro convertemos o vo em entity salvamos e mandamos o vo
		//mas pq ? simples se vc quer esconder seu objeto voce tem que retornar um VO é só ler o codigo direitinho que vc lembra.
		var entity =  DozerMapper.parseObject(person, Person.class);
		var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		
		vo.add(linkTo(methodOn(PersonControler.class).findById(vo.getKey())).withSelfRel());
		
	return vo; 

		
	}
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with v2!");
		
		var entity =  mapper.convertVoToEntity(person); 
		var vo =  mapper.convetEntityToVo(repository.save(entity));
		
		return vo;
		
		
	}
	
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating one person!");
		
	var entity = repository.findById(person.getKey())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
	
	entity.setFirstName(person.getFirstName());
	entity.setAddress(person.getAddress());
	entity.setGender(person.getGender());
	entity.setLastName(person.getLastName());
	
	var vo =  DozerMapper.parseObject(repository.save(entity), PersonVO.class);
	
	vo.add(linkTo(methodOn(PersonControler.class).findById(vo.getKey())).withSelfRel());
	
		return vo; 
		
	}
	public void delete(Long id) {
		logger.info("Deleting one person!");
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
			repository.delete(entity);
			
		
	}
	
	// bussines rules
}
