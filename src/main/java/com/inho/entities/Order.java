package com.inho.entities;

import com.inho.entities.valuetypes.Address;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name="Orders")
@SequenceGenerator(name="order_id_seq_generator", sequenceName = "order_id_seq",
        initialValue = 1, allocationSize = 1)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq_generator")
    private Long id;

    private int orderAmount;

    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name="city"      , column = @Column(name="city")),
            @AttributeOverride(name="street"    , column = @Column(name="street")),
            @AttributeOverride(name="zipcode"   , column = @Column(name="zipcode"))
    })
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;
}
