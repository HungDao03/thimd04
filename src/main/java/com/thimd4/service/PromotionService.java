package com.thimd4.service;

import com.thimd4.model.Promotion;
import com.thimd4.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PromotionService implements IPromotionService {

    @Autowired
    private PromotionRepository promotionRepository;


    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Promotion save(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public void delete(Long id) {
        promotionRepository.deleteById(id);
    }

    @Override
    public Promotion findById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }
    @Override
    public List<Promotion> search(Double discount, LocalDate start, LocalDate end) {
        return promotionRepository.search(discount, start, end);
    }

}


