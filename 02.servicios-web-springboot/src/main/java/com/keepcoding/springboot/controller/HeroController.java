package com.keepcoding.springboot.controller;

import com.keepcoding.springboot.dao.HeroService;
import com.keepcoding.springboot.exceptions.HeroNotFoundException;
import com.keepcoding.springboot.dao.HeroDaoService;
import com.keepcoding.springboot.model.Hero;
import com.keepcoding.springboot.model.Power;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
public class HeroController {

    @Autowired()
    @Qualifier("jpa")
    private HeroService heroService;

    @GetMapping("/hero")
    public List<Hero> findAllHeroes() {
        return heroService.findAll();
    }

    @GetMapping("/hero/{id}")
    public Hero findHeroById(@PathVariable int id) {

        Hero result = heroService.findHeroById(id);
        if(result == null) {
            throw new HeroNotFoundException("El héroe con id " + id + " no existe.");
        }
        return result;
    }

    @DeleteMapping("/hero/{id}")
    public void deleteHeroById(@PathVariable int id) {
        Hero result = heroService.findHeroById(id);
        if(result == null) {
            throw new HeroNotFoundException("El héroe con id " + id + " no existe.");
        }
        heroService.deleteHero(id);
    }

    @GetMapping("/hero/{heroId}/power")
    public List<Power> findAllPowersByHeroId(@PathVariable int heroId) {

        List<Power> result = heroService.findAllPowersByHeroId(heroId);
        if(result == null) {
            throw new HeroNotFoundException("El heroe con id " + heroId + " no existe.");
        }
        return result;
    }

    @PostMapping("/hero/{heroId}/power")
    public ResponseEntity<Object> addPower(@PathVariable int heroId, @RequestBody @Valid Power power) {
        Power addedPower = heroService.addPower(heroId, power);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{powerId}")
                .buildAndExpand(addedPower.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }


    @GetMapping("/hero/{heroId}/power/{powerId}")
    public Power findPowerById(@PathVariable int heroId, @PathVariable int powerId) {

        Power result = heroService.findPowerById(heroId, powerId);
        if(result == null) {
            throw new HeroNotFoundException("El poder con id " + powerId + " no existe.");
        }
        return result;
    }



    @DeleteMapping("/hero/{heroId}/power/{powerId}")
    public void deletePowerById(@PathVariable int heroId, @PathVariable int powerId) {
        Power result = heroService.findPowerById(heroId, powerId);
        if(result == null) {
            throw new HeroNotFoundException("El poder con id " + powerId + " no existe.");
        }
        heroService.deletePower(heroId, powerId);
    }



}
