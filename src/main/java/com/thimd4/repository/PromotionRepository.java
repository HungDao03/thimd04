package com.thimd4.repository;

import com.thimd4.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByDiscount(Double discount);

    @Query("SELECT p FROM Promotion p WHERE (:discount IS NULL OR p.discount = :discount)")
    List<Promotion> search(@Param("discount") Double discount);
}