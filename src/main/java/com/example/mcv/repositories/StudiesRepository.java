package com.example.mcv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mcv.entities.Studies;

@Repository
public interface StudiesRepository extends JpaRepository<Studies, Integer> {

}
