package ru.eltech.oop.budgeapp;

import java.time.LocalDate;
import java.util.Objects;

public class Request {
    private final Category category;
    private final LocalDate from;
    private final LocalDate to;

    public Request(Category category, LocalDate from, LocalDate to) {
        this.category = Objects.requireNonNull(category);
        this.from = Objects.requireNonNull(from);
        this.to = Objects.requireNonNull(to);
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}
