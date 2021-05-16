package com.example.mcv.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mcv.dto.Mensaje;
import com.example.mcv.dto.ResumeDto;
import com.example.mcv.entities.Experience;
import com.example.mcv.entities.Resume;
import com.example.mcv.entities.Skills;
import com.example.mcv.entities.Studies;
import com.example.mcv.repositories.StudiesRepository;
import com.example.mcv.security.jwt.JwtProvider;
import com.example.mcv.services.ResumeService;
import com.example.mcv.services.SkillsService;
import com.example.mcv.services.StudiesService;
import com.example.mcv.services.ExperienceService;


@RestController
@RequestMapping("/resume")
@CrossOrigin(origins = "http://localhost:4200")
public class ResumeController {

    @Autowired
    JwtProvider jwtProvider;
	
	@Autowired
	ResumeService resumeService;
	
	@Autowired
	ExperienceService experienceService;
	
	@Autowired
	StudiesService studiesService;
	
	@Autowired
	SkillsService skillsService;
	
	@GetMapping()
	public ResponseEntity<List<Resume>> list() {
		List<Resume> list = resumeService.list();
		return new ResponseEntity<List<Resume>> (list, HttpStatus.OK);
	}
	
	@GetMapping( path = "/{nombre}")
	public ResponseEntity<Resume> getByNombre(@PathVariable("nombre") String nombre) {
		if(!resumeService.existsByNombreUsuario(nombre))
			return new ResponseEntity<Resume>(HttpStatus.NOT_FOUND);

		Resume resume = resumeService.getByNombreUsuario(nombre).get();
		return new ResponseEntity<Resume>(resume, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping()
	public ResponseEntity<?> create(@RequestBody ResumeDto resumeDto, HttpServletRequest req) {
		String token = getToken(req);
		String _jwtUser = jwtProvider.getNombreUsuarioFromToken(token);

		if(resumeService.existsByNombreUsuario(_jwtUser))
			return new ResponseEntity(new Mensaje("ya existe un currículum para este usuario"), HttpStatus.INTERNAL_SERVER_ERROR);
		
		if(StringUtils.isBlank(resumeDto.getNombre()))
			return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
		
		Resume resume = new Resume(resumeDto.getNombre(), _jwtUser, resumeDto.getFono(), resumeDto.getEmail(), resumeDto.getAvatar(), resumeDto.getBanner(), resumeDto.getFechaNacimiento(),
				resumeDto.getNacionalidad(), resumeDto.getDireccion(), resumeDto.getNombreEmpresa(), resumeDto.getDireccionEmpresa(), resumeDto.getIntroduccionCarta(), resumeDto.getCuerpoCarta());

		Set<Experience> experiences = new HashSet<>();
		resumeDto.getExperiences().forEach((p)-> {
			Experience experience = new Experience(p.getNombre(), p.getDescripcion(),p.getFechaInicio(),p.getFechaFin());
			experienceService.save(experience);
			experiences.add(experience);
		});

		resume.setExperiences(experiences);
		
		Set<Studies> studies = new HashSet<>();
		resumeDto.getStudies().forEach((p)-> {
			Studies study = new Studies(p.getNombre(), p.getDescripcion(),p.getFechaInicio(),p.getFechaFin());
			studiesService.save(study);
			studies.add(study);
		});

		resume.setStudies(studies);
		
		Set<Skills> skills = new HashSet<>();
		resumeDto.getSkills().forEach((p)-> {
			Skills skill = new Skills(p.getNombre(), p.getNota());
			skillsService.save(skill);
			skills.add(skill);
		});

		resume.setSkills(skills);
		
		resumeService.save(resume);
		return new ResponseEntity(new Mensaje("currículum creado"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping()
	public ResponseEntity<?> update(@RequestBody ResumeDto resumeDto, HttpServletRequest req) {
		String token = getToken(req);
		String _jwtUser = jwtProvider.getNombreUsuarioFromToken(token);
		
		if(StringUtils.isBlank(resumeDto.getNombre()))
			return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);

		Resume resume = resumeService.getByNombreUsuario(_jwtUser).get();
		resume.setNombre(resumeDto.getNombre());
		resume.setFono(resumeDto.getFono());
		resume.setEmail(resumeDto.getEmail());
		resume.setAvatar(resumeDto.getAvatar());
		resume.setBanner(resumeDto.getBanner());
		resume.setFechaNacimiento(resumeDto.getFechaNacimiento());
		resume.setNacionalidad(resumeDto.getNacionalidad());
		resume.setDireccion(resumeDto.getDireccion());
		resume.setNombreEmpresa(resumeDto.getNombreEmpresa());
		resume.setDireccionEmpresa(resumeDto.getDireccionEmpresa());
		resume.setIntroduccionCarta(resumeDto.getIntroduccionCarta());
		resume.setCuerpoCarta(resumeDto.getCuerpoCarta());

		resumeDto.getExperiences().forEach((p)-> {
			if(p.getId() > 0) {
				Experience experience = experienceService.getOne(p.getId()).get();
				experience.setNombre(p.getNombre());
				experience.setDescripcion(p.getDescripcion());
				experience.setFechaInicio(p.getFechaInicio());
				experience.setFechaFin(p.getFechaFin());
				experienceService.save(experience);

			} else {
				Experience experience = new Experience(p.getNombre(), p.getDescripcion(),p.getFechaInicio(),p.getFechaFin());
				experienceService.save(experience);
				resume.addExperiences(experience);
			}
		});
		
		resumeDto.getStudies().forEach((p)-> {
			if(p.getId() > 0) {
				Studies study = studiesService.getOne(p.getId()).get();
				study.setNombre(p.getNombre());
				study.setDescripcion(p.getDescripcion());
				study.setFechaInicio(p.getFechaInicio());
				study.setFechaFin(p.getFechaFin());
				studiesService.save(study);

			} else {
				Studies study = new Studies(p.getNombre(), p.getDescripcion(),p.getFechaInicio(),p.getFechaFin());
				studiesService.save(study);
				resume.addtStudies(study);
			}
		});
		
		resumeDto.getSkills().forEach((p)-> {
			if(p.getId() > 0) {
				Skills skill = skillsService.getOne(p.getId()).get();
				skill.setNombre(p.getNombre());
				skill.setNota(p.getNota());
				skillsService.save(skill);

			} else {
				Skills skill = new Skills(p.getNombre(), p.getNota());
				skillsService.save(skill);
				resume.addtSkills(skill);
			}
		});

		resumeService.save(resume);
		return new ResponseEntity(new Mensaje("currículum actualizado"), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping()
	public ResponseEntity<?> delete(HttpServletRequest req) {
		String token = getToken(req);
		String _jwtUser = jwtProvider.getNombreUsuarioFromToken(token);
		
		if(!resumeService.existsByNombreUsuario(_jwtUser))
			return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);

		resumeService.delete(resumeService.getByNombreUsuario(_jwtUser).get().getId());
		return new ResponseEntity(new Mensaje("currículum eliminado"), HttpStatus.OK);
	}

    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
