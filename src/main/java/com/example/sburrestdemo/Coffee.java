package com.example.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coffee {

    @Id
    @GeneratedValue
    private Long coffeeId;

    private String coffeeName;

    @JsonIgnore
    @BatchSize(size=1000)
    @OneToMany(mappedBy = "coffee", cascade = ALL)
    private List<CoffeeStatus> coffeeStatusList;

}

