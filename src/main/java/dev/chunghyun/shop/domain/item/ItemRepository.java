package dev.chunghyun.shop.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Items i JOIN FETCH i.itemPricesList WHERE i.itemNumber = :itemNumber")
    Optional<Item> findByItemNumberWithItemPrices(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Items i JOIN FETCH i.itemStocks WHERE i.itemNumber = :itemNumber")
    Optional<Item> findByItemNumberWithItemStocks(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Items i JOIN FETCH i.itemPricesList")
    List<Item> findAllWithItemPrices();
}