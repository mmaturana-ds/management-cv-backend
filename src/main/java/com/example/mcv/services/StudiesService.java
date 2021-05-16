package com.example.mcv.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mcv.entities.Studies;
import com.example.mcv.repositories.StudiesRepository;

@Service
@Transactional
public class StudiesService {

    @Autowired
    StudiesRepository studiesRepository;

	public Optional<Studies> getOne(int id) {
		return studiesRepository.findById(id);
	}

    public void save(Studies studies){
    	studiesRepository.save(studies);
    }
    
	public void delete(int id) {
		studiesRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return studiesRepository.existsById(id);
	}
}
