package com.example.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeStatus {

    @Id
    @GeneratedValue
    private Long coffeeStatusId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="coffee_id")
    private Coffee coffee;

    private String coffeeStatus;

}
