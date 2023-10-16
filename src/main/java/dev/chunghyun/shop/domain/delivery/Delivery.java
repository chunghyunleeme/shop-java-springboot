package dev.chunghyun.shop.domain.delivery;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.orders.Orders;
import jakarta.persistence.*;

@Entity
public class Delivery extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "city")
    private String city;
    @Column(name = "street")
    private String street;
    @Column(name = "zipcode")
    private String zipcode;
    @Column(name = "status")
    private String status;
}
