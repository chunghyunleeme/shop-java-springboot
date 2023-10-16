package dev.chunghyun.shop.domain.member;

import dev.chunghyun.shop.domain.Address;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.List;
@Entity
public class Member extends BaseTimeEntity{
    @Id
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "name")
    private String name;
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "HOME_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "HOME_ZIPCODE"))
    })
    @Embedded
    private Address homeAddress;
}
