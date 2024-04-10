package com.cribhub.backend.DTO;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CribDTOTest {

    private Crib crib;
    private CribDTO cribDTO;

    @BeforeEach
    public void setUp() {
        crib = new Crib();
        crib.setCribId(1L);
        crib.setName("Test Crib");

        List<Customer> members = new ArrayList<>();
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test User");
        members.add(customer);
        crib.setCribMembers(members);

        List<ShoppingListItem> items = new ArrayList<>();
        ShoppingListItem shoppingListItem = new ShoppingListItem();
        shoppingListItem.setId(1L);
        shoppingListItem.setName("Test Shopping List");
        items.add(shoppingListItem);
        crib.setShoppingList(items);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("Test Task");
        tasks.add(task);
        crib.setTasks(tasks);

        cribDTO = CribDTO.ConvertToCribDTO(crib);
    }

    @Test
    public void testCribId() {
        assertEquals(crib.getCribId(), cribDTO.getCribId());
    }

    @Test
    public void testCribName() {
        assertEquals(crib.getName(), cribDTO.getCribName());
    }

    @Test
    public void testCribMembers() {
        assertNotNull(cribDTO.getCribMembers());
        assertEquals(crib.getCribMembers().size(), cribDTO.getCribMembers().size());
        assertEquals(crib.getCribMembers().get(0).getUserId(), cribDTO.getCribMembers().get(0).getId());
        assertEquals(crib.getCribMembers().get(0).getUserName(), cribDTO.getCribMembers().get(0).getName());
    }

    @Test
    public void testShoppingListItems() {
        assertNotNull(cribDTO.getShoppingListItemItems());
        assertEquals(crib.getShoppingList(), cribDTO.getShoppingListItemItems());
    }
}