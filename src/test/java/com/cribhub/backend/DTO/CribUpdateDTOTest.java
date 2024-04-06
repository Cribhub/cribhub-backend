package com.cribhub.backend.DTO;

import com.cribhub.backend.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CribUpdateDTOTest {

    private CribUpdateDTO cribUpdateDTO;

    @BeforeEach
    public void setUp() {
        List<Customer> cribMemberIds = new ArrayList<>();
        Customer customer = new Customer();
        customer.setUserId(1L);
        customer.setUserName("Test User");
        cribMemberIds.add(customer);

        String cribName = "Test Crib";
        cribUpdateDTO = new CribUpdateDTO(cribName, cribMemberIds);
    }

    @Test
    public void testCribName() {
        String cribName = "Test Crib";
        assertEquals(cribName, cribUpdateDTO.getCribName());
    }

    @Test
    public void testCribMemberIds() {
        assertNotNull(cribUpdateDTO.getCribMemberIds());
        assertEquals(1, cribUpdateDTO.getCribMemberIds().size());
        assertEquals(1L, cribUpdateDTO.getCribMemberIds().get(0).getUserId());
        assertEquals("Test User", cribUpdateDTO.getCribMemberIds().get(0).getUserName());
    }

    @Test
    public void testSetCribName() {
        String newCribName = "New Test Crib";
        cribUpdateDTO.setCribName(newCribName);
        assertEquals(newCribName, cribUpdateDTO.getCribName());
    }

    @Test
    public void testSetCribMemberIds() {
        List<Customer> newCribMemberIds = new ArrayList<>();
        Customer newCustomer = new Customer();
        newCustomer.setUserId(2L);
        newCustomer.setUserName("New Test User");
        newCribMemberIds.add(newCustomer);

        cribUpdateDTO.setCribMemberIds(newCribMemberIds);
        assertNotNull(cribUpdateDTO.getCribMemberIds());
        assertEquals(1, cribUpdateDTO.getCribMemberIds().size());
        assertEquals(2L, cribUpdateDTO.getCribMemberIds().get(0).getUserId());
        assertEquals("New Test User", cribUpdateDTO.getCribMemberIds().get(0).getUserName());
    }
}