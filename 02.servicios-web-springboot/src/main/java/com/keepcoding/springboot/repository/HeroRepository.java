package com.keepcoding.springboot.repository;

import com.keepcoding.springboot.model.Hero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeroRepository extends JpaRepository<Hero, Integer> {
}
