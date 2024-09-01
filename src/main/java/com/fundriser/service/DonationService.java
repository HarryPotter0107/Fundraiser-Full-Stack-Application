package com.fundriser.service;

import com.fundriser.models.DonationsModel;
import com.fundriser.repos.DonationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationsRepo donationsRepo;

    public List<DonationsModel> findAllDonations() {
        return donationsRepo.findAll();
    }

    public DonationsModel saveDonation(DonationsModel donation) {
        return donationsRepo.save(donation);
    }
}
