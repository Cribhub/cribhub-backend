package com.cribhub.backend.repositories;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingList, Long> {
    ShoppingList findShoppingListsByCrib(Crib crib);
}