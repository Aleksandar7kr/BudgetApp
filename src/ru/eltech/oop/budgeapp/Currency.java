package ru.eltech.oop.budgeapp;

public enum Currency {
    RUB("₽"),

    USD("$"),

    EUR("€");

    private final String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
