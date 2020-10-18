package ru.eltech.oop.budgeapp;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DumbBudgetManager implements BudgetManager {
    private final Set<Category> categories = new HashSet<>();
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    @Override
    public Set<Category> getAllCategories() {
        return Collections.unmodifiableSet(categories);
    }

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public void removeTransaction(Transaction transaction) {
        transactions.remove(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    @Override
    public List<Transaction> getTransactions(LocalDate from, LocalDate to) {
        return transactions
                .stream()
                .filter(t ->
                        t.getDate().isAfter(from.minusDays(1)) &&
                                t.getDate().isBefore(to.plusDays(1))
                )
                .collect(Collectors.toList());
    }
}
