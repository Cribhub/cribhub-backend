package com.cribhub.backend.services.intefaces;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface CribService {
    Crib saveCrib(Crib crib) throws CribNameAlreadyTakenException;

    Crib updateCrib(Crib crib) throws CribNameAlreadyTakenException, CribNotFoundException;

    Crib getCribById(Long cribId) throws CribNotFoundException;

    void addMember(Long cribId, Long customerId) throws CribNotFoundException, CustomerNotFoundException;

    List<Crib> getAllCribs();

    void deleteCrib(Long cribId);
}
