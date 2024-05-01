package com.example.sburrestdemo;

import jakarta.persistence.EntityManager;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CoffeeService {

    private final CoffeeRepository coffeeRepository;
    private final EntityManager entityManager;

    @Transactional(readOnly = true)
    public Stream<Coffee> find(Long from, Long to) {
        return coffeeRepository.findByCoffeeIdBetween(from, to);
    }

    @Transactional(readOnly = true)
    public List<CoffeeDto.Response> findList(Long from, Long to) {
        return coffeeRepository.findByCoffeeIdBetween(from, to)
                .map(CoffeeDto.Response::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Stream<CoffeeDto.Response> findStream(Long from, Long to) {
        return coffeeRepository.findByCoffeeIdBetween(from, to)
                .map(coffee -> {
                    var result = CoffeeDto.Response.of(coffee);
                    entityManager.detach(coffee);
                    return result;
                });
    }

}
