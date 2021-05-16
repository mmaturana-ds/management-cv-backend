package com.example.mcv.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mcv.entities.Skills;
import com.example.mcv.repositories.SkillsRepository;

@Service
@Transactional
public class SkillsService {

    @Autowired
    SkillsRepository skillsRepository;

	public Optional<Skills> getOne(int id) {
		return skillsRepository.findById(id);
	}

    public void save(Skills skills){
    	skillsRepository.save(skills);
    }
    
	public void delete(int id) {
		skillsRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return skillsRepository.existsById(id);
	}
}
