package ru.eltech.oop.budgeapp;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Category category = new Category("Транспорт", Color.BLUE, "🚎");
        Category category2 = new Category("Отпуск", Color.GREEN, "🌴");
        Category category3 = new Category("Еда", Color.red, "🍔");

        Currency rub = Currency.RUB;

        Transaction transaction = new Transaction(
                10.0,
                LocalDate.now(),
                category,
                Currency.RUB,
                ""
        );

        BudgetManager manager = new DumbBudgetManager();
        manager.addCategory(category);
        manager.addCategory(category2);
        manager.addCategory(category3);

        manager.addTransaction(transaction);
        manager.addTransaction(new Transaction(
                100.0,
                LocalDate.of(2020, Month.APRIL, 23),
                category2,
                Currency.EUR,
                ""
        ));


        List<Transaction> transactions = manager.getTransactions(
                LocalDate.of(2020, Month.MARCH, 1),
                LocalDate.now().minusDays(1)
        );

        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
