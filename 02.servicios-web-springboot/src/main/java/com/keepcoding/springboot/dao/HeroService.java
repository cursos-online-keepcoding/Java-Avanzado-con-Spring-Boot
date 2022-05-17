package com.keepcoding.springboot.dao;
import com.keepcoding.springboot.model.Hero;
import com.keepcoding.springboot.model.Power;

import java.util.List;

public interface HeroService {
    List<Hero> findAll();
    Hero findHeroById(int id);
    Hero addHero(Hero hero);
    void deleteHero(int id);

    Power addPower(int heroId, Power power);
    Power findPowerById(int heroId, int powerId);
    List<Power> findAllPowersByHeroId(int heroId);
    void deletePower(int heroId, int powerId);
}
