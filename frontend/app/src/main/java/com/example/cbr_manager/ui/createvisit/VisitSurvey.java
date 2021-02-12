package com.example.cbr_manager.ui.createvisit;

import java.util.ArrayList;
import java.util.Date;

public class VisitSurvey {

    enum Goal {
        CANCELLED,
        ONGOING,
        CONCLUDED
    }

    enum DDL {
        BIDIBIDI_ZONE_1,
        BIDIBIDI_ZONE_2,
        BIDIBIDI_ZONE_3,
        BIDIBIDI_ZONE_4,
        BIDIBIDI_ZONE_5,
        PALORINYA_BASECAMP,
        PALORINYA_ZONE_1,
        PALORINYA_ZONE_2,
        PALORINYA_ZONE_3
    }

    enum Purpose {
        CBR,
        DISABILITY_REFERRAL,
        DISABILITY_REFERRAL_FOLLOW_UP
    }

    enum CBR {
        HEALTH,
        EDUCATION,
        SOCIAL
    }

    // Purpose
    boolean isCBR;
    boolean isDisabilityReferral;
    boolean isDisabilityReferralFollowUp;

    // If CBR
    boolean isHealth;
    boolean isEducation;
    boolean isSocial;

    Date dateOfVisit;

    String nameCBRWorker;

    String locationOfVisit;

    String locationDDL;

    int villageNumber;
}
