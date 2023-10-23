package dev.chunghyun.shop.category;

import dev.chunghyun.shop.BaseTimeEntity;
import dev.chunghyun.shop.item.Item;
import jakarta.persistence.*;
import static jakarta.persistence.FetchType.*;

@Entity
public class CategoryItem extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = LAZY)
    private Category category;

    @ManyToOne(fetch = LAZY)
    private Item item;
}
