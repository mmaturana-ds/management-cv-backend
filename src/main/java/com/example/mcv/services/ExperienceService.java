package com.example.mcv.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mcv.entities.Experience;
import com.example.mcv.repositories.ExperienceRepository;

@Service
@Transactional
public class ExperienceService {

    @Autowired
    ExperienceRepository experienceRepository;

	public Optional<Experience> getOne(int id) {
		return experienceRepository.findById(id);
	}

    public void save(Experience experience){
    	experienceRepository.save(experience);
    }
    
	public void delete(int id) {
		experienceRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return experienceRepository.existsById(id);
	}
}
