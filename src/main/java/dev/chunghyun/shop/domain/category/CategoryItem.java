package dev.chunghyun.shop.domain.category;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.item.Item;
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
