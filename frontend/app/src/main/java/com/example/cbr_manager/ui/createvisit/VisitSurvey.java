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

    private String surveyClientName;
    private String purposeOfVisit;
    private String ifCBR;
    private Date date;
    private String cbrWorkerName;
    private String locationOfVisit;
    private DDL locationDDL;
    private int villageNumber;
    private ArrayList<HealthProvision> healthProvisions = new ArrayList<>();
    private Goal goalMetHealthProvision;
    private String conclusionOutcomeHealthProvision;
    private ArrayList<SocialProvision> socialProvision = new ArrayList<>();
    private Goal goalMetSocialProvision;
    private String conclusionOutComeSocialProvision;

}
