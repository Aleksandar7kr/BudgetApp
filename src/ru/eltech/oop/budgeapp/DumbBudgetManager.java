package ru.eltech.oop.budgeapp;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class DumbBudgetManager implements BudgetManager {
    private static final Category EMPTY = new Category("Без категории", Color.WHITE, "");

    private final Set<Category> categories = new HashSet<>();
    private final List<Transaction> transactions = new ArrayList<>();

    // map category to its parent
    private final Map<Category, Category> parents = new HashMap<>();

    // map category to its set of subcategories
    private final Map<Category, Set<Category>> subCategories = new HashMap<>();

    public DumbBudgetManager() {
        categories.add(EMPTY);
    }

    @Override
    public Category createCategory(String name, Color color, String icon) {
        return createCategory(name, color, icon, EMPTY);
    }

    @Override
    public Category createCategory(String name, Color color, String icon, Category parent) {
        Category newCategory = new Category(name, color, icon);

        categories.add(newCategory);

        if (categories.contains(parent)) {
            parents.put(newCategory, parent);
            subCategories
                    .computeIfAbsent(parent, aLong -> new HashSet<>())
                    .add(newCategory);
        }

        return newCategory;
    }

    @Override
    public void removeCategory(Category categoryToRemove) {
        // remove category
        boolean removed = categories.remove(categoryToRemove);

        // remove info about parent
        Category newTransactionCategory = EMPTY;
        if (removed) {
            Category parent = parents.remove(categoryToRemove);
            Set<Category> parentSubcategories = subCategories.get(parent);
            if (parent != null && parentSubcategories != null) {
                parentSubcategories.remove(categoryToRemove);
                newTransactionCategory = parent;
            }
        }

        // remove info about subcategories
        Set<Category> subs = subCategories.get(categoryToRemove);
        if (subs != null) {
            for (Category subCategory : subs) {
                parents.remove(subCategory);
                parents.put(subCategory, newTransactionCategory);
            }
        }

        // update transaction
        List<Transaction> transactionsToUpdate = this.transactions.stream()
                .filter(transaction -> transaction.getCategory().equals(categoryToRemove))
                .collect(Collectors.toList());

        for (Transaction t : transactionsToUpdate) {
            transactions.remove(t);
            transactions.add(new Transaction(
                    t.getAmount(),
                    t.getDate(),
                    newTransactionCategory,
                    t.getCurrency(),
                    t.getComment()
            ));
        }
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
        return getTransactions(new Request(EMPTY, LocalDate.MIN, LocalDate.MAX));
    }

    @Override
    public List<Transaction> getTransactions(Request request) {
        Category requestedCategory = request.getCategory();

        Set<Category> allSuitableCategories = new HashSet<>();
        findSuitableCategories(allSuitableCategories, requestedCategory);

        LocalDate from = request.getFrom();
        LocalDate to = request.getTo();

        return transactions
                .stream()
                .filter(t -> allSuitableCategories.contains(t.getCategory()))
                .filter(t -> {
                    LocalDate date = t.getDate();
                    return date.equals(from) || date.equals(to) || isBetweenFromTo(from, to, date);
                })
                .collect(Collectors.toList());
    }

    private boolean isBetweenFromTo(LocalDate from, LocalDate to, LocalDate date) {
        return date.isAfter(from) && date.isBefore(to);
    }

    private void findSuitableCategories(Set<Category> suitableCategories, Category current) {
        suitableCategories.add(current);
        Set<Category> subs = subCategories.get(current);
        if (subs != null) {
            for (Category sub : subs) {
                findSuitableCategories(suitableCategories, sub);
            }
        }
    }
}
