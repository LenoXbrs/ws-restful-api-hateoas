package br.com.lenox.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lenox.data.vo.v1.PersonVO;
import br.com.lenox.data.vo.v2.PersonVOV2;
import br.com.lenox.services.PersonServices;


@RestController
@RequestMapping(value = "api/person/v1")
public class PersonControler {

	//injetado
  @Autowired
  private PersonServices service;
	
	
  //diferente das query Strings, tem que por esse value pra poder aceitar o method=RequestMethod.GET.
	@GetMapping(	// produzir json e xml
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public List<PersonVO> findAll(	
			) {
		return  service.findAll();
	}
  
  @GetMapping(value = "/{id}",
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public PersonVO findById(
			@PathVariable(value = "id") Long id

			) {
		return service.findById(id);
	}

	@PostMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	//recebe parametros via body
	public PersonVO create(@RequestBody PersonVO person ) {
		return service.create(person);
	}
	@PostMapping(value = "/v2",
			consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	//recebe parametros via body
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person ) {
		return service.createV2(person);
	}
	@PutMapping(
			consumes = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	//recebe parametros via body
	public PersonVO update(@RequestBody PersonVO person ) {
		return service.update(person);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(
			@PathVariable(value = "id") Long id
				
			) {
		 service.delete(id);
		 //lança codio 204 -> é um ok e nao tem nada para retornar
		 return ResponseEntity.noContent().build();
	}
	
	

	
	
	

	
	
	




	
	
}
