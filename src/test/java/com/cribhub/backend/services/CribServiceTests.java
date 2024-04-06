package com.cribhub.backend.services;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.repositories.CribRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CribServiceTests {
    private CribRepository cribRepository;
    private CribServiceImpl cribService;

    @BeforeEach
    public void setUp() {
        cribRepository = Mockito.mock(CribRepository.class);
        cribService = new CribServiceImpl(cribRepository);
    }

    @Test
    public void testSaveCrib() {
        Crib crib = new Crib();
        when(cribRepository.save(crib)).thenReturn(crib);

        Crib savedCrib = cribService.saveCrib(crib);

        assertNotNull(savedCrib);
        verify(cribRepository, times(1)).save(crib);
    }

    @Test
    public void testGetCribById() {
        Crib crib = new Crib();
        when(cribRepository.findById(1L)).thenReturn(Optional.of(crib));

        Crib foundCrib = cribService.getCribById(1L);

        assertNotNull(foundCrib);
        verify(cribRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllCribs() {
        Crib crib1 = new Crib();
        Crib crib2 = new Crib();
        when(cribRepository.findAll()).thenReturn(Arrays.asList(crib1, crib2));

        List<Crib> cribs = cribService.getAllCribs();

        assertEquals(2, cribs.size());
        verify(cribRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCrib() {
        doNothing().when(cribRepository).deleteById(1L);

        cribService.deleteCrib(1L);

        verify(cribRepository, times(1)).deleteById(1L);
    }
}