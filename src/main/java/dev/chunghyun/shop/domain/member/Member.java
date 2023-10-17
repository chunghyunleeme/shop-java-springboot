package dev.chunghyun.shop.domain.member;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.address.AddressEntity;
import dev.chunghyun.shop.domain.order.Order;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
@Getter
@Entity
public class Member extends BaseTimeEntity{
    @Id
    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "member_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addressHistory = new ArrayList<>();
}
