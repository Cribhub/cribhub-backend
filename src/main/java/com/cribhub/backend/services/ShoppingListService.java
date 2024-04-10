package com.cribhub.backend.services;


import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.ShoppingListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceNotFoundException;
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
    public Optional<ShoppingListItem> createShoppingListForCrib(Long cribId, ShoppingListItem shoppingListItem) {
        Optional<Crib> cribOptional = cribRepository.findById(cribId);
        if (cribOptional.isEmpty()) {
            return Optional.empty();
        }

        Crib crib = cribOptional.get();
        shoppingListItem.setCrib(crib);
        ShoppingListItem savedShoppingListItem = shoppingListRepository.save(shoppingListItem);
        return Optional.of(savedShoppingListItem);
    }

    public Optional<ShoppingListItem> getShoppingListById(Long id) {
        return shoppingListRepository.findById(id);
    }

    public ShoppingListItem createOrUpdateShoppingList(ShoppingListItem shoppingListItem) {
        return shoppingListRepository.save(shoppingListItem);
    }

    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }
}