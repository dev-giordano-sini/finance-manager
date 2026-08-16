package it.financemanager.category;

/** Framework-neutral command shared by create and update category use cases. */
public record SaveCategoryCommand(String name, String color) { }
