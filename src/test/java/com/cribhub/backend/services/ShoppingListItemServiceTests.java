package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.repositories.ShoppingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ShoppingListItemServiceTests {

    @InjectMocks
    ShoppingListService shoppingListService;

    @Mock
    ShoppingListRepository shoppingListRepository;

    @Mock
    CribRepository cribRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createShoppingListForCribTest() {
        Crib crib = new Crib();
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(cribRepository.findById(1L)).thenReturn(Optional.of(crib));
        when(shoppingListRepository.save(shoppingListItem)).thenReturn(shoppingListItem);

        Optional<ShoppingListItem> result = shoppingListService.createShoppingListForCrib(1L, shoppingListItem);

        assertNotNull(result);
        assertEquals(shoppingListItem, result.get());
        verify(cribRepository, times(1)).findById(1L);
        verify(shoppingListRepository, times(1)).save(shoppingListItem);
    }

    @Test
    public void getShoppingListByIdTest() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingListItem));

        Optional<ShoppingListItem> result = shoppingListService.getShoppingListById(1L);

        assertNotNull(result);
        assertEquals(shoppingListItem, result.get());
        verify(shoppingListRepository, times(1)).findById(1L);
    }

    @Test
    public void createOrUpdateShoppingListTest() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(shoppingListRepository.save(shoppingListItem)).thenReturn(shoppingListItem);

        ShoppingListItem result = shoppingListService.createOrUpdateShoppingList(shoppingListItem);

        assertNotNull(result);
        assertEquals(shoppingListItem, result);
        verify(shoppingListRepository, times(1)).save(shoppingListItem);
    }

    @Test
    public void createShoppingListForNonExistentCribTest() {
        ShoppingListItem shoppingListItem = new ShoppingListItem();

        when(cribRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ShoppingListItem> result = shoppingListService.createShoppingListForCrib(1L, shoppingListItem);

        assertEquals(Optional.empty(), result);
        verify(cribRepository, times(1)).findById(1L);
    }

    @Test
    public void deleteShoppingListTest() {
        doNothing().when(shoppingListRepository).deleteById(1L);

        shoppingListService.deleteShoppingList(1L);

        verify(shoppingListRepository, times(1)).deleteById(1L);
    }
}