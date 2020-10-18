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

    @Override
    public Category createCategory(String name, Color color, String icon) {
        return createCategory(name, color, icon, EMPTY);
    }

    @Override
    public Category createCategory(String name, Color color, String icon, Category parent) {
        Category newCategory = new Category(name, color, icon);

        categories.add(newCategory);

        if (parent != null && categories.contains(parent)) {
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
                parents.remove(categoryToRemove);
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

    @Override
    public List<Transaction> getByCategory(Category category) {
        Set<Category> allSuitableCategories = new HashSet<>();
        findSuitableCategories(allSuitableCategories, category);

        return transactions.stream()
                .filter(transaction -> allSuitableCategories.contains(transaction.getCategory()))
                .collect(Collectors.toList());
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
