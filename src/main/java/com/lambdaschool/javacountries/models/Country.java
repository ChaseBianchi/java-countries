package com.lambdaschool.javacountries.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long countryid;
    private String name;
    private long population;
    private long landmasskm2;
    private int medianage;

    public Country() {
    }


}
