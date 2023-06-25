package one.terenin.yookassa.common;

import lombok.Data;

public enum Currency {
    RUB("RUB"),
    EUR("EUR"),
    USD("USD");

    private final String info;

    Currency(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
