package com.cribhub.backend.DTO;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.ShoppingList;
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
        crib.setCribName("Test Crib");

        List<Customer> members = new ArrayList<>();
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test User");
        members.add(customer);
        crib.setCribMembers(members);

        List<ShoppingList> items = new ArrayList<>();
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListId(1L);
        shoppingList.setShoppingName("Test Shopping List");
        items.add(shoppingList);
        crib.setShoppingListItems(items);

        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setTaskId(1L);
        task.setTaskName("Test Task");
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
        assertEquals(crib.getCribName(), cribDTO.getCribName());
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
        assertNotNull(cribDTO.getShoppingListItems());
        assertEquals(crib.getShoppingListItems(), cribDTO.getShoppingListItems());
    }

    @Test
    public void testTaskId() {
        assertNotNull(cribDTO.getTaskId());
        assertEquals(crib.getTasks().size(), cribDTO.getTaskId().size());
        assertEquals(crib.getTasks().get(0).getTaskId(), cribDTO.getTaskId().get(0));
    }
}