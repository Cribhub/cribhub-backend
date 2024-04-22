package com.cribhub.backend.repositories;

import com.cribhub.backend.domain.Crib;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface CribRepository extends CrudRepository<Crib, Long> {
    Optional<Crib> findByName(String cribName);
}
