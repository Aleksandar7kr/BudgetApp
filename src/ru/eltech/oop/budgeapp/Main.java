package ru.eltech.oop.budgeapp;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;

public class Main {

    public static void main(String[] args) {
        BudgetManager manager = new DumbBudgetManager();

        init(manager);

        manager.getAllCategories().stream().findAny().ifPresent(category -> manager.removeCategory(category));

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
        Category transport = manager.createCategory("Транспорт", Color.BLUE, "🚎");

        Category taxi = manager.createCategory("Такси", Color.YELLOW, "🚕", transport);
        Category carsharing = manager.createCategory("Каршеринг", Color.GREEN, "🚗", transport);
        Category cityTransport = manager.createCategory("ОТ", Color.MAGENTA, "🚌", transport);

        Category vacation = manager.createCategory("Отпуск", Color.GREEN, "🌴");
        manager.createCategory("Авиабилеты", Color.CYAN, "✈", vacation);
        manager.createCategory("Отели", Color.CYAN, "🏨", vacation);

        Category food = manager.createCategory("Еда", Color.red, "🍔");
        Category supermarkets = manager.createCategory("Супермаркеты", Color.YELLOW, "🛒");
        Category sport = manager.createCategory("Спорт", Color.ORANGE, "🏀");

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
