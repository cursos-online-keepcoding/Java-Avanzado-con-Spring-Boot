package com.keepcoding.springboot.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
public class Hero {

    @Id
    @GeneratedValue
    private int id;
    @Size(min = 3, message = "El tamaño debe ser mayor que 2")
    @NotNull(message = "First Name cannot be null")
    private String name;
    private String heroName;
    @Past
    private Date birthDate;


    @OneToMany(orphanRemoval = true, mappedBy = "hero", fetch = FetchType.LAZY)
    private List<Power> powers;

    public Hero() {
    }

    public Hero(int id, String name, String heroName, Date birthDate) {
        this.id = id;
        this.name = name;
        this.heroName = heroName;
        this.birthDate = birthDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Power> getPowers() {
        return powers;
    }

    public void setPowers(List<Power> powers) {
        this.powers = powers;
    }
}

