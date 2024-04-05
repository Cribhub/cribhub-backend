package com.cribhub.backend.repositories;


import com.cribhub.backend.domain.Crib;
import org.springframework.data.repository.CrudRepository;

public interface CribRepository extends CrudRepository<Crib, Long> {

    Crib findByCribId(long userId);

}
