package com.innodealing.model.dm.bond;

public enum BondDiscoveryNetPriceEnum {
    DEAL(1, "dealNetPrice"),
    BID(2, "bidNetPrice"),
    OFFER(3, "ofrNetPrice");

    private final int code;
    private final String value;

    BondDiscoveryNetPriceEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
