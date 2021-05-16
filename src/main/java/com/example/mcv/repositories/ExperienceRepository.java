package com.example.mcv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mcv.entities.Experience;


@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {

}
