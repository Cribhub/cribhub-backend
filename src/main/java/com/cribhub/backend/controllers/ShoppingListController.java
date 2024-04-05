package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.ShoppingList;
import com.cribhub.backend.services.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shopping")
@CrossOrigin
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long id) {
        return shoppingListService.getShoppingListById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("crib/{cribId}")
    public ResponseEntity<ShoppingList> createShoppingListForCrib(@PathVariable Long cribId, @RequestBody ShoppingList shoppingList) {
        Optional<ShoppingList> savedShoppingList = shoppingListService.createShoppingListForCrib(cribId, shoppingList);
        return savedShoppingList
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(@PathVariable Long id, @RequestBody ShoppingList shoppingList) {
        return shoppingListService.getShoppingListById(id)
                .map(existingList -> {
                    existingList.setShoppingName(shoppingList.getShoppingName());
                    existingList.setShoppingDescription(shoppingList.getShoppingDescription());
                    // Update other fields as necessary
                    ShoppingList updatedShoppingList = shoppingListService.createOrUpdateShoppingList(existingList);
                    return ResponseEntity.ok(updatedShoppingList);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return ResponseEntity.noContent().build();
    }
}