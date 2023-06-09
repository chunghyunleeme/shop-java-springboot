package dev.chunghyun.order.domain.items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ItemsRepository extends JpaRepository<Items, Long> {
    @Query("SELECT i FROM Items i JOIN FETCH i.itemPricesList WHERE i.itemNumber = :itemNumber")
    Optional<Items> findByItemNumberWithItemPrices(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Items i JOIN FETCH i.itemStocks WHERE i.itemNumber = :itemNumber")
    Optional<Items> findByItemNumberWithItemStocks(@Param("itemNumber") int itemNumber);

    @Query("SELECT i FROM Items i JOIN FETCH i.itemPricesList")
    List<Items> findAllWithItemPrices();
}