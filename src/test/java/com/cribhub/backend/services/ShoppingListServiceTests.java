package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.ShoppingList;
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

public class ShoppingListServiceTests {

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
        ShoppingList shoppingList = new ShoppingList();

        when(cribRepository.findById(1L)).thenReturn(Optional.of(crib));
        when(shoppingListRepository.save(shoppingList)).thenReturn(shoppingList);

        Optional<ShoppingList> result = shoppingListService.createShoppingListForCrib(1L, shoppingList);

        assertNotNull(result);
        assertEquals(shoppingList, result.get());
        verify(cribRepository, times(1)).findById(1L);
        verify(shoppingListRepository, times(1)).save(shoppingList);
    }

    @Test
    public void getShoppingListByIdTest() {
        ShoppingList shoppingList = new ShoppingList();

        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        Optional<ShoppingList> result = shoppingListService.getShoppingListById(1L);

        assertNotNull(result);
        assertEquals(shoppingList, result.get());
        verify(shoppingListRepository, times(1)).findById(1L);
    }

    @Test
    public void createOrUpdateShoppingListTest() {
        ShoppingList shoppingList = new ShoppingList();

        when(shoppingListRepository.save(shoppingList)).thenReturn(shoppingList);

        ShoppingList result = shoppingListService.createOrUpdateShoppingList(shoppingList);

        assertNotNull(result);
        assertEquals(shoppingList, result);
        verify(shoppingListRepository, times(1)).save(shoppingList);
    }

    @Test
    public void createShoppingListForNonExistentCribTest() {
        ShoppingList shoppingList = new ShoppingList();

        when(cribRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ShoppingList> result = shoppingListService.createShoppingListForCrib(1L, shoppingList);

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