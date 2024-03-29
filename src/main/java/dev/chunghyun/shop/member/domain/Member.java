package dev.chunghyun.shop.member.domain;

import dev.chunghyun.shop.BaseTimeEntity;
import dev.chunghyun.shop.address.AddressEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Member extends BaseTimeEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column
    private String name;

    @Column
    private String password;

    @JoinColumn(name = "member_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AddressEntity> addressHistory = new ArrayList<>();

    @Builder
    public Member(String name, String password){
        this.name = name;
        this.password = password;
    }

    public void update(String name){
        this.name = name;
    }
}
