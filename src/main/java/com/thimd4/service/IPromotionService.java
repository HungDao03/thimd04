package com.thimd4.service;

import com.thimd4.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface IPromotionService {
    List<Promotion> getAll();
    Promotion save(Promotion promotion);
    void delete(Long id);
    Promotion findById(Long id);
    List<Promotion> search(Double discount, LocalDate start, LocalDate end);
}
