package dev.chunghyun.shop.domain.member;

import dev.chunghyun.shop.domain.address.Address;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.address.AddressEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Member extends BaseTimeEntity{
    @Id
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "MEMBER_ID")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addressHistory = new ArrayList<>();
}
