package com.fundriser.service;

import com.fundriser.models.FundriserModel;
import com.fundriser.repos.FundriserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundraiserService {

    @Autowired
    private FundriserRepo fundriserRepo;

    public List<FundriserModel> findAllFundraisers() {
        return fundriserRepo.findAll();
    }

    public Optional<FundriserModel> findById(String id) {
        return fundriserRepo.findById(id);
    }

    public FundriserModel saveFundraiser(FundriserModel fundraiser) {
        return fundriserRepo.save(fundraiser);
    }
}
