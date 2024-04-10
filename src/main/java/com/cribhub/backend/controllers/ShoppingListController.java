package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.services.ShoppingListService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shopping")
@CrossOrigin
@Log4j2
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingListItem> getShoppingListById(@PathVariable Long id) {
        Optional<ShoppingListItem> shoppingListOptional = shoppingListService.getShoppingListById(id);

        // If the shopping list is not found, return a 404 Not Found response
        if(shoppingListOptional.isEmpty()) {
            log.error("Shopping list item with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Shopping list item retrieved: id-{} name-{}", id, shoppingListOptional.get().getName());
        return ResponseEntity.ok(shoppingListOptional.get());
    }

    @PostMapping("crib/{cribId}")
    public ResponseEntity<ShoppingListItem> createShoppingListForCrib(@PathVariable Long cribId, @RequestBody ShoppingListItem shoppingListItem) {
        Optional<ShoppingListItem> savedShoppingList = shoppingListService.createShoppingListForCrib(cribId, shoppingListItem);

        // If the shopping list is not saved, return a 500 internal server error
        if(savedShoppingList.isEmpty()) {
            log.error("Unable to create shopping list item. Verify that the crib with id {} exists and that the shopping list item is valid", cribId);
            return ResponseEntity.internalServerError().build();
        }

        log.info("Shopping list item created: id-{} name-{}", savedShoppingList.get().getId(), savedShoppingList.get().getName());
        return ResponseEntity.ok(savedShoppingList.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingListItem> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingListItem shoppingListItem) {
        log.info("Updating shopping list with id {}", id);

        // Check if the shopping list item exists
        Optional<ShoppingListItem> shoppingListOptional = shoppingListService.getShoppingListById(id);
        if  (shoppingListOptional.isEmpty()) {
            log.error("Can not update shopping list item with id {} because it does not exist.", id);
            return ResponseEntity.notFound().build();
        }

        ShoppingListItem existingList = shoppingListOptional.get();
        existingList.setName(shoppingListItem.getName());
        existingList.setDescription(shoppingListItem.getDescription());
        // Update other fields as necessary
        ShoppingListItem updatedShoppingListItem = shoppingListService.createOrUpdateShoppingList(existingList);

        log.info("Shopping list item updated: id-{} name-{}", id, updatedShoppingListItem.getName());
        return ResponseEntity.ok(updatedShoppingListItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        log.warn("Shopping list with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}