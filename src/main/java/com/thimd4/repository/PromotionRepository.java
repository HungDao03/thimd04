package com.thimd4.repository;

import com.thimd4.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    List<Promotion> findByDiscount(Double discount);
    List<Promotion> findByStartDate(LocalDate startDate);
    List<Promotion> findByEndDate(LocalDate endDate);

    @Query("SELECT p FROM Promotion p WHERE " +
            "(:discount IS NULL OR p.discount = :discount) AND " +
            "(:start IS NULL OR p.startDate = :start) AND " +
            "(:end IS NULL OR p.endDate = :end)")
    List<Promotion> search(@Param("discount") Double discount,
                           @Param("start") LocalDate start,
                           @Param("end") LocalDate end);
}
