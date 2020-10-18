package ru.eltech.oop.budgeapp

import java.awt.Color

data class Category(
        val name: String,
        val color: Color,
        val icon: String
) {
    override fun toString(): String {
        return "$icon $name"
    }
}