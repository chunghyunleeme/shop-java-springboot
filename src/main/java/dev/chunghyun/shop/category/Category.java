package dev.chunghyun.shop.category;

import dev.chunghyun.shop.BaseTimeEntity;
import jakarta.persistence.*;
import java.util.List;
import static jakarta.persistence.FetchType.*;

@Entity
public class Category extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> childs;
}
