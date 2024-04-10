package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.ShoppingListController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.services.ShoppingListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ShoppingListItemControllerTests {

    @InjectMocks
    ShoppingListController shoppingListController;

    @Mock
    ShoppingListService shoppingListService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getShoppingListByIdTest() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(shoppingListService.getShoppingListById(1L)).thenReturn(Optional.of(shoppingListItem));

        ResponseEntity<ShoppingListItem> result = shoppingListController.getShoppingListById(1L);

        assertNotNull(result);
        assertEquals(shoppingListItem, result.getBody());
        verify(shoppingListService, times(1)).getShoppingListById(1L);
    }

    @Test
    public void createShoppingListForCribTest() {
        Crib crib = new Crib();
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(shoppingListService.createShoppingListForCrib(1L, shoppingListItem)).thenReturn(Optional.of(shoppingListItem));

        ResponseEntity<ShoppingListItem> result = shoppingListController.createShoppingListForCrib(1L, shoppingListItem);

        assertNotNull(result);
        assertEquals(shoppingListItem, result.getBody());
        verify(shoppingListService, times(1)).createShoppingListForCrib(1L, shoppingListItem);
    }

    @Test
    public void updateShoppingListTest() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(shoppingListService.getShoppingListById(1L)).thenReturn(Optional.of(shoppingListItem));
        when(shoppingListService.createOrUpdateShoppingList(shoppingListItem)).thenReturn(shoppingListItem);

        ResponseEntity<ShoppingListItem> result = shoppingListController.updateShoppingList(1L, shoppingListItem);

        assertNotNull(result);
        assertEquals(shoppingListItem, result.getBody());
        verify(shoppingListService, times(1)).getShoppingListById(1L);
        verify(shoppingListService, times(1)).createOrUpdateShoppingList(shoppingListItem);
    }

    @Test
    public void deleteShoppingListTest() {
        doNothing().when(shoppingListService).deleteShoppingList(1L);

        ResponseEntity<Void> result = shoppingListController.deleteShoppingList(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(shoppingListService, times(1)).deleteShoppingList(1L);
    }
}