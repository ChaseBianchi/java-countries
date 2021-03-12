package com.lambdaschool.javacountries.controllers;

import com.lambdaschool.javacountries.models.Country;
import com.lambdaschool.javacountries.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CountryController {
    @Autowired
    CountryRepository countryrep;

    private List<Country> findCountry(List<Country> myList, CheckCountry tester){
        List<Country> tempList = new ArrayList<>();
        for (Country c: myList){
            if(tester.test(c)){
                tempList.add(c);
            }
        }
        return tempList;
    }

    // http://localhost:2019/names/all
    @GetMapping(value= "/names/all", produces = {"application/json"})
    public ResponseEntity<?> listAllCountries(){
        List<Country> myList = new ArrayList<>();
        countryrep.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/start/u
    @GetMapping(value= "/start/{letter}", produces = {"application/json"})
    public ResponseEntity<?> filterByFirstLetter(@PathVariable char letter){
        List<Country> myList = new ArrayList<>();
        countryrep.findAll().iterator().forEachRemaining(myList::add);
        myList.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        List<Country> rtnList = findCountry(myList, c -> c.getName().charAt(0) == letter);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/population/total
    @GetMapping(value= "/population/total", produces = {"application/json"})
    public ResponseEntity<?> totalPopulation(){
        List<Country> myList = new ArrayList<>();
        countryrep.findAll().iterator().forEachRemaining(myList::add);
        long population = myList.stream().mapToLong(Country::getPopulation).sum();
        System.out.println("The Total Population is " + population);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/population/min
    @GetMapping(value= "population/min", produces = {"application/json"})
    public ResponseEntity<?> lowPop(){
        List<Country> myList = new ArrayList<>();
        countryrep.findAll().iterator().forEachRemaining(myList::add);
        List<Country> sortedList = myList
                .stream()
                .sorted((c1, c2) -> Long.compare(c1.getPopulation(), c2.getPopulation()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(sortedList, HttpStatus.OK);
    }


}
