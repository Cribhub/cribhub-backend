package com.cribhub.backend.services;


import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingList;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.ShoppingListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final CribRepository cribRepository;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository, CribRepository cribRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.cribRepository = cribRepository;
    }

    @Transactional
    public Optional<ShoppingList> createShoppingListForCrib(Long cribId, ShoppingList shoppingList) {
        Optional<Crib> cribOptional = cribRepository.findById(cribId);
        if (!cribOptional.isPresent()) {
            return Optional.empty();
        }

        Crib crib = cribOptional.get();
        shoppingList.setCrib(crib);
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);
        return Optional.of(savedShoppingList);
    }

    public Optional<ShoppingList> getShoppingListById(Long id) {
        return shoppingListRepository.findById(id);
    }

    public ShoppingList createOrUpdateShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }
}