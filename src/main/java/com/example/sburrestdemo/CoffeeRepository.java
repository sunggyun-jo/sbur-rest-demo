package com.example.sburrestdemo;

import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;


public interface CoffeeRepository extends CrudRepository<Coffee, Long> {

    Stream<Coffee> findByCoffeeIdBetween(Long from, Long to);

}
