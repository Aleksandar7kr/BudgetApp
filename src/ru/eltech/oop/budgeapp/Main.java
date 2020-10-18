package ru.eltech.oop.budgeapp;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        BudgetManager manager = new DumbBudgetManager();

        init(manager);

        System.out.println("Все категории:");
        for (Category category : manager.getAllCategories()) {
            System.out.println(category);
        }

        System.out.println();
        System.out.println("Все расходы:");
        for (Transaction transaction : manager.getAllTransactions()) {
            System.out.println(transaction);
        }

    }

    private static void init(BudgetManager manager) {
        Category transport = new Category("Транспорт", Color.BLUE, "🚎");
        Category vacation = new Category("Отпуск", Color.GREEN, "🌴");
        Category food = new Category("Еда", Color.red, "🍔");
        Category supermarkets = new Category("Супермаркеты", Color.YELLOW, "🛒");
        Category sport = new Category("Спорт", Color.ORANGE, "🏀");

        manager.addCategory(transport);
        manager.addCategory(vacation);
        manager.addCategory(food);
        manager.addCategory(supermarkets);
        manager.addCategory(sport);

        manager.addTransaction(new Transaction(
                10.0,
                LocalDate.now(),
                transport,
                Currency.RUB,
                ""
        ));

        manager.addTransaction(new Transaction(
                1000.0,
                LocalDate.of(2020, Month.APRIL, 30),
                vacation,
                Currency.EUR,
                ""
        ));

        manager.addTransaction(new Transaction(
                800.0,
                LocalDate.now().minusDays(1),
                food,
                Currency.RUB,
                "test"
        ));

        manager.addTransaction(new Transaction(
                20.1,
                LocalDate.of(2020, Month.SEPTEMBER, 2),
                sport,
                Currency.USD,
                "test"
        ));
    }
}
