package dev.chunghyun.shop.domain.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "address")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;

    private Address address;
}
