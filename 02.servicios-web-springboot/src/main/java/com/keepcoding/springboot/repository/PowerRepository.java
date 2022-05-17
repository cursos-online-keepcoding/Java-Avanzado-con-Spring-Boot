package com.keepcoding.springboot.repository;

import com.keepcoding.springboot.model.Power;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PowerRepository extends JpaRepository<Power, Integer> {
}
