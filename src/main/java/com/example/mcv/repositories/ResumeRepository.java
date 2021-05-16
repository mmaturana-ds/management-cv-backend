package com.example.mcv.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mcv.entities.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
	Optional<Resume> findByNombreUsuario(String nombreUsuario);
	boolean existsByNombreUsuario(String nombreUsuario);
}
