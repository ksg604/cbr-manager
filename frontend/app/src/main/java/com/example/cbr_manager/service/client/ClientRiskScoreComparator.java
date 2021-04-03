package com.example.cbr_manager.service.client;

import java.util.Comparator;

public class ClientRiskScoreComparator implements Comparator<Client> {

    private SortOrder sortOrder;

    public ClientRiskScoreComparator(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Client o1, Client o2) {
        if (this.sortOrder == SortOrder.ASCENDING) {
            return o1.calculateRiskScore() - o2.calculateRiskScore();
        }
        return o2.calculateRiskScore() - o1.calculateRiskScore();

    }

    public enum SortOrder {ASCENDING, DESCENDING}
}
