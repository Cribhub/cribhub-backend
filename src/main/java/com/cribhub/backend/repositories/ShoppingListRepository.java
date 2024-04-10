package com.cribhub.backend.repositories;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingListItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends CrudRepository<ShoppingListItem, Long> {
    ShoppingListItem findShoppingListsByCrib(Crib crib);
}