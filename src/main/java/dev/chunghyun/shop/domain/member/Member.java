package dev.chunghyun.shop.domain.member;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.List;
@Entity
public class Member extends BaseTimeEntity{
    @Id
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "name")
    private String name;
    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;
    @Column(name = "zipcode")
    private String zipcode;
}
