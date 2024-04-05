package com.cribhub.backend.controllers;

import com.cribhub.backend.DTO.CribDTO;
import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.services.CribService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cribs")
@CrossOrigin
public class CribController {
    private final CribService cribService;

    public CribController(CribService cribService) {
        this.cribService = cribService;
    }

    @PostMapping
    public ResponseEntity<CribDTO> createCrib(@RequestBody Crib crib) {
        crib = cribService.saveCrib(crib);
        return ResponseEntity.status(HttpStatus.CREATED).body(CribDTO.ConvertToCribDTO(crib));
    }

    @GetMapping("/{cribId}")
    public ResponseEntity<CribDTO> getCribById(@PathVariable Long cribId) {
        Crib crib = cribService.getCribById(cribId);
        if (crib == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(CribDTO.ConvertToCribDTO(crib));
    }

    @DeleteMapping("/{cribId}")
    public ResponseEntity<Void> deleteCrib(@PathVariable Long cribId) {
        cribService.deleteCrib(cribId);
        return ResponseEntity.noContent().build();
    }

}