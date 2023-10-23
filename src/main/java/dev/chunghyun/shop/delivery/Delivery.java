package dev.chunghyun.shop.delivery;

import dev.chunghyun.shop.BaseTimeEntity;
import dev.chunghyun.shop.address.Address;
import jakarta.persistence.*;

@Entity
public class Delivery extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "DELIVERY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "DELIVERY_STREET")),
            @AttributeOverride(name = "ZIPCODE", column = @Column(name = "DELIVERY_ZIPCODE"))
    })
    @Embedded
    private Address address;
}
