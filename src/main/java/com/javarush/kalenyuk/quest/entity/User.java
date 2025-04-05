package com.javarush.kalenyuk.quest.entity;

public class User {
    private String id; // Текущий узел квеста (например, "q1", "q1.1")
    private int count; // Счётчик количества игр
    private String name; // Имя пользователя

    // Конструкторы
    public User() {
    }

    public User(String id, int count, String name) {
        this.id = id;
        this.count = count;
        this.name = name;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
