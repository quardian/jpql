package com.inho.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private int age;


    public UserDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public UserDTO(String name) {
        this.name = name;
    }

    public UserDTO(int age) {
        this.age = age;
    }
}
