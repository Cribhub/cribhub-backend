package com.cribhub.backend.services;


import com.cribhub.backend.domain.Crib;

import java.util.List;

public interface CribService {
    Crib saveCrib(Crib crib);
    Crib getCribById(Long cribId);
    List<Crib> getAllCribs();
    void deleteCrib(Long cribId);
}