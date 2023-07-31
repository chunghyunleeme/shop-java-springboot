package dev.chunghyun.shop.domain.category;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.items.Items;
import jakarta.persistence.*;

@Entity
public class CategoryItem extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Items items;
}
