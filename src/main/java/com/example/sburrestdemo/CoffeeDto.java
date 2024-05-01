package com.example.sburrestdemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

public class CoffeeDto {

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Response {

        private Long coffeeId;

        private String coffeeName;

        private int count;

        public static Response of(Coffee coffee) {
            return Response.builder()
                    .coffeeId(coffee.getCoffeeId())
                    .coffeeName(coffee.getCoffeeName())
                    .count(coffee.getCoffeeStatusList().size())
                    .build();
        }
    }
}