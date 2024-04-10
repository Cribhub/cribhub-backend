package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.ShoppingList;
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
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long id) {
        Optional<ShoppingList> shoppingListOptional = shoppingListService.getShoppingListById(id);

        // If the shopping list is not found, return a 404 Not Found response
        if(shoppingListOptional.isEmpty()) {
            log.error("Shopping list item with id {} not found", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Shopping list item retrieved: id {} {}", id, shoppingListOptional.get().getShoppingName());
        return ResponseEntity.ok(shoppingListOptional.get());
    }

    @PostMapping("crib/{cribId}")
    public ResponseEntity<ShoppingList> createShoppingListForCrib(@PathVariable Long cribId, @RequestBody ShoppingList shoppingList) {
        Optional<ShoppingList> savedShoppingList = shoppingListService.createShoppingListForCrib(cribId, shoppingList);

        // If the shopping list is not saved, return a 500 internal server error
        if(savedShoppingList.isEmpty()) {
            log.error("Unable to create shopping list item. Verify that the crib with id {} exists and that the shopping list item is valid", cribId);
            return ResponseEntity.internalServerError().build();
        }

        log.info("Shopping list item created: id {} {}", savedShoppingList.get().getShoppingListId(), savedShoppingList.get().getShoppingName());
        return ResponseEntity.ok(savedShoppingList.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingList shoppingList) {
        log.info("Updating shopping list with id {}", id);

        // Check if the shopping list item exists
        Optional<ShoppingList> shoppingListOptional = shoppingListService.getShoppingListById(id);
        if  (shoppingListOptional.isEmpty()) {
            log.error("Can not update shopping list item with id {} because it does not exist.", id);
            return ResponseEntity.notFound().build();
        }

        ShoppingList existingList = shoppingListOptional.get();
        existingList.setShoppingName(shoppingList.getShoppingName());
        existingList.setShoppingDescription(shoppingList.getShoppingDescription());
        // Update other fields as necessary
        ShoppingList updatedShoppingList = shoppingListService.createOrUpdateShoppingList(existingList);

        log.info("Shopping list item updated: id {} {}", id, updatedShoppingList.getShoppingName());
        return ResponseEntity.ok(updatedShoppingList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);

        log.warn("Shopping list with id {} deleted", id);
        return ResponseEntity.noContent().build();
    }
}