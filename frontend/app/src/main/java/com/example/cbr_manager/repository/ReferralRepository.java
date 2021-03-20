package com.example.cbr_manager.repository;

import com.example.cbr_manager.service.referral.ReferralDao;

import javax.inject.Inject;

public class ReferralRepository {

    private ReferralDao authDetailDao;
    private String authHeader;

    @Inject
    ReferralRepository(ReferralDao authDetailDao, String authHeader) {
        this.authDetailDao = authDetailDao;
        this.authHeader = authHeader;
    }

}
