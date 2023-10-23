package dev.chunghyun.shop.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i JOIN FETCH i.itemPriceList WHERE i.itemNumber = :itemNumber")
    Optional<Item> findByItemNumberWithItemPrices(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Item i JOIN FETCH i.itemStock WHERE i.itemNumber = :itemNumber")
    Optional<Item> findByItemNumberWithItemStocks(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Item i JOIN FETCH i.itemPriceList")
    List<Item> findAllWithItemPrices();
}