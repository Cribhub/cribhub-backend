package com.cribhub.backend.services.intefaces;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNotFoundException;

import java.util.List;

public interface CribService {
    Crib saveCrib(Crib crib) throws CribNameAlreadyTakenException;

    Crib getCribById(Long cribId) throws CribNotFoundException;

    List<Crib> getAllCribs();

    void deleteCrib(Long cribId);
}
