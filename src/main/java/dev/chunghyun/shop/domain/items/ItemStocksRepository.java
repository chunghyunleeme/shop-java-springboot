package dev.chunghyun.order.domain.items;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemStocksRepository extends JpaRepository<ItemStocks, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT is FROM ItemStocks is WHERE is.id = :id")
    Optional<ItemStocks> findByIdForUpdate(@Param("id") Long id);
}
