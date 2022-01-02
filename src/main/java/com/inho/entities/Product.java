package com.inho.entities;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@SequenceGenerator(name="product_id_seq_generator", sequenceName = "product_id_seq",
        initialValue = 1, allocationSize = 1)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq_generator")
    private Long id;

    private String name;

    private int price;

    private int stockAmount;
}
