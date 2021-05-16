package com.example.mcv.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mcv.entities.Resume;
import com.example.mcv.repositories.ExperienceRepository;
import com.example.mcv.repositories.ResumeRepository;
import com.example.mcv.repositories.SkillsRepository;
import com.example.mcv.repositories.StudiesRepository;

@Service
@Transactional
public class ResumeService {
	@Autowired
	ResumeRepository resumeRepository;
	
	@Autowired
	ExperienceRepository experienceRepository;
	
	@Autowired
	StudiesRepository studiesRepository;
	
	@Autowired
	SkillsRepository skillsRepository;
	
	public List<Resume> list() {
		return resumeRepository.findAll();
	}
	
	public Optional<Resume> getOne(int id) {
		return resumeRepository.findById(id);
	}
	
	public Optional<Resume> getByNombreUsuario(String nombreUsuario) {
		return resumeRepository.findByNombreUsuario(nombreUsuario);
	}
	
	public void save(Resume resume) {
		resumeRepository.save(resume);
	}
	
	public void delete(int id) {
		resumeRepository.deleteById(id);
	}
	
	public boolean existsById(int id) {
		return resumeRepository.existsById(id);
	}

	public boolean existsByNombreUsuario(String nombreUsuario) {
		return resumeRepository.existsByNombreUsuario(nombreUsuario);
	}
}
