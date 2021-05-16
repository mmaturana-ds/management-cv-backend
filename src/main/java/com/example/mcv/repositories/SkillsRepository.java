package com.example.mcv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mcv.entities.Skills;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Integer> {

}
