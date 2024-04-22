package com.cribhub.backend.services;

import com.cribhub.backend.domain.Crib;
import com.cribhub.backend.exceptions.CribNameAlreadyTakenException;
import com.cribhub.backend.exceptions.CribNotFoundException;
import com.cribhub.backend.repositories.CribRepository;
import com.cribhub.backend.services.intefaces.CribService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CribServiceImpl implements CribService {
    private final CribRepository cribRepository;

    public CribServiceImpl(CribRepository cribRepository) {
        this.cribRepository = cribRepository;
    }

    @Override
    public Crib saveCrib(Crib crib) throws CribNameAlreadyTakenException {
        if (cribRepository.findByName(crib.getName()).isPresent()) {
            throw new CribNameAlreadyTakenException(crib.getName());
        }

        return cribRepository.save(crib);
    }

    @Override
    public Crib getCribById(Long cribId) throws CribNotFoundException {
        Crib crib = cribRepository.findById(cribId).orElse(null);
        if (crib == null) {
            throw new CribNotFoundException(cribId);
        }

        return cribRepository.findById(cribId).orElse(null);
    }

    @Override
    public List<Crib> getAllCribs() {
        return (List<Crib>) cribRepository.findAll();
    }

    @Override
    public void deleteCrib(Long cribId) {
        cribRepository.deleteById(cribId);
    }
}
