package ru.eltech.oop.budgeapp

enum class Currency(val symbol: String) {
    RUB("₽"),
    USD("$"),
    EUR("€");

    override fun toString(): String {
        return symbol
    }
}