package ru.eltech.oop.budgeapp;

import java.time.LocalDate;
import java.time.Month;

public class RequestBuilder {
    private Category category = BudgetManager.EMPTY;
    private LocalDate from = LocalDate.MIN;
    private LocalDate to = LocalDate.MAX;

    public RequestBuilder() {
    }

    public RequestBuilder(Request request) {
        withCategory(request.getCategory())
                .withTo(request.getTo())
                .withFrom(request.getFrom());
    }

    public RequestBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public RequestBuilder withFrom(LocalDate from) {
        this.from = from;
        return this;
    }

    public RequestBuilder withFrom(int year, Month month, int dayOfMonth) {
        return withFrom(LocalDate.of(year, month, dayOfMonth));
    }

    public RequestBuilder withTo(LocalDate to) {
        this.to = to;
        return this;
    }

    public RequestBuilder withTo(int year, Month month, int dayOfMonth) {
        return withTo(LocalDate.of(year, month, dayOfMonth));
    }

    public Request build() {
        return new Request(category, from, to);
    }
}