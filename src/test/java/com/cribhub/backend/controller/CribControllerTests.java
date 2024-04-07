package com.cribhub.backend.controller;

import com.cribhub.backend.controllers.CribController;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.DTO.CribDTO;
import com.cribhub.backend.services.CribService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CribControllerTests {

    @InjectMocks
    CribController cribController;

    @Mock
    CribService cribService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createCribTest() {
        Crib crib = new Crib();
        when(cribService.saveCrib(crib)).thenReturn(crib);

        ResponseEntity<CribDTO> result = cribController.createCrib(crib);

        assertNotNull(result);
        assertEquals(201, result.getStatusCodeValue());
        verify(cribService, times(1)).saveCrib(crib);
    }

    @Test
    public void getCribByIdTest() {
        Crib crib = new Crib();
        when(cribService.getCribById(1L)).thenReturn(crib);

        ResponseEntity<CribDTO> result = cribController.getCribById(1L);

        assertNotNull(result);
        assertEquals(200, result.getStatusCodeValue());
        verify(cribService, times(1)).getCribById(1L);
    }

    @Test
    public void deleteCribTest() {
        doNothing().when(cribService).deleteCrib(1L);

        ResponseEntity<Void> result = cribController.deleteCrib(1L);

        assertEquals(204, result.getStatusCodeValue());
        verify(cribService, times(1)).deleteCrib(1L);
    }
}