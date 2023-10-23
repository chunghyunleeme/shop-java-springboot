package dev.chunghyun.shop.item;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemStockRepository extends JpaRepository<ItemStock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT is FROM ItemStock is WHERE is.id = :id")
    Optional<ItemStock> findByIdForUpdate(@Param("id") Long id);
}
