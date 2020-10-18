package ru.eltech.oop.budgeapp;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BudgetManager {
    void addCategory(Category category);

    void removeCategory(Category category);

    Set<Category> getAllCategories();

    void addTransaction(Transaction transaction);

    void removeTransaction(Transaction transaction);

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactions(LocalDate from, LocalDate to);
}
