package com.keepcoding.springboot.dao;

import com.keepcoding.springboot.model.Hero;
import com.keepcoding.springboot.model.Power;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("dao")
public class HeroDaoService implements HeroService {

    private static List<Hero> heroes = new ArrayList<>();

    static {
        heroes.add(new Hero(1, "Peter Parker", "Spiderman", new Date()));
        heroes.add(new Hero(2, "Tony Stark", "Ironman", new Date()));
        heroes.add(new Hero(3, "Bruce Banner", "Hulk", new Date()));
    }


    private static int counter = 3;

    @Override
    public List<Hero> findAll() {
        return heroes;
    }

    @Override
    public Hero findHeroById(int id) {
        Hero result = null;
        for (Hero hero : heroes) {
            if (hero.getId() == id) {
                result = hero;
            }
        }
        return result;
    }

    @Override
    public Hero addHero(Hero hero) {
        hero.setId(++counter);
        heroes.add(hero);
        return hero;
    }

    @Override
    public void deleteHero(int id) {
        Iterator<Hero> heroIterator = heroes.iterator();
        Hero heroToRemove = null;
        do {
            heroToRemove = heroIterator.next();
            if (heroToRemove.getId() == id) {
                heroIterator.remove();
            }
        } while (heroIterator.hasNext());
    }

    @Override
    public Power addPower(int heroId, Power power) {
        return null;
    }

    @Override
    public Power findPowerById(int heroId, int powerId) {
        return null;
    }

    @Override
    public List<Power> findAllPowersByHeroId(int heroId) {
        return null;
    }

    @Override
    public void deletePower(int heroId, int powerId) {

    }
}
