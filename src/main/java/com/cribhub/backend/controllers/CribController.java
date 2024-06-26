package com.cribhub.backend.controllers;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.domain.Customer;
import com.cribhub.backend.domain.ShoppingListItem;
import com.cribhub.backend.domain.Task;
import com.cribhub.backend.dto.*;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.services.intefaces.CribService;
import jakarta.validation.constraints.Min;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cribs")
@CrossOrigin
@Validated
@Log4j2
public class CribController {
    private final CribService cribService;

    public CribController(CribService cribService) {
        this.cribService = cribService;
    }

    @PostMapping
    public ResponseEntity<CribDTO> createCrib(@RequestBody @Valid CreateCribDTO createCribDTO)
            throws CribNameAlreadyTakenException {
        Crib crib = new Crib();
        crib.setName(createCribDTO.getCribName());

        Crib newCrib = cribService.saveCrib(crib);
        log.info("Crib with id {} created", newCrib.getCribId());
        return ResponseEntity.status(HttpStatus.CREATED).body(CribDTO.ConvertToCribDTO(newCrib));
    }

    @GetMapping("/{cribId}")
    public ResponseEntity<CribDTO> getCribById(@PathVariable @Min(1) long cribId) throws CribNotFoundException {
        Crib crib = cribService.getCribById(cribId);

        log.info("Crib with id {} retrieved", cribId);
        return ResponseEntity.ok(CribDTO.ConvertToCribDTO(crib));
    }

    @GetMapping("/{cribId}/tasks")
    public ResponseEntity<List<TaskDTO>> getCribTasksById(@PathVariable long cribId) throws CribNotFoundException {
        Crib crib = cribService.getCribById(cribId);

        List<Task> tasks = crib.getTasks();
        List<TaskDTO> taskDTOs = tasks.stream()
                .map(TaskDTO::TaskUpdateDTO)
                .collect(Collectors.toList());

        log.info("Tasks for crib with id {} retrieved", cribId);
        return ResponseEntity.ok(taskDTOs);
    }

    @GetMapping("/{cribId}/shoppingListItems")
    public ResponseEntity<List<ShoppingListItemDTO>> getShoppingListItemsByCribId(@PathVariable long cribId) throws CribNotFoundException {
        Crib crib = cribService.getCribById(cribId);

        List<ShoppingListItem> shoppingListItems = crib.getShoppingList();
        List<ShoppingListItemDTO> shoppingListItemDTOs = shoppingListItems.stream()
                .map(ShoppingListItemDTO::ConvertToDTO)
                .collect(Collectors.toList());

        log.info("Shopping list items for crib with id {} retrieved", cribId);
        return ResponseEntity.ok(shoppingListItemDTOs);
    }

    @DeleteMapping("/{cribId}")
    public ResponseEntity<Void> deleteCrib(@PathVariable Long cribId) {
        cribService.deleteCrib(cribId);

        log.warn("Crib with id {} deleted", cribId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cribId}/members")
    public ResponseEntity<List<CustomerDTO>> getCribMembers(@PathVariable @Min(1) long cribId)
            throws CribNotFoundException {
        List<Customer> members = cribService.getMembers(cribId);
        List<CustomerDTO> response = members.stream().map(customer -> CustomerDTO.ConvertToDTO(customer))
                .collect(Collectors.toList());

        log.info("Retrieved members for crib with id {}", cribId);
        return ResponseEntity.ok(response);
    }
}
