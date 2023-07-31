package dev.chunghyun.shop.domain.category;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private List<Category> childs;
}
